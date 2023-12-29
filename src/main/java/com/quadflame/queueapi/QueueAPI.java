package com.quadflame.queueapi;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class QueueAPI {
    private static final Map<String, AbstractQueue<?>> queues = new HashMap<>();

    public static void createQueue(AbstractQueue<?> queue) {
        if(queues.containsKey(queue.getName())) throw new IllegalArgumentException("Queue with name " + queue.getName() + " already exists!");
        queues.put(queue.getName(), queue);
    }

    public static void deleteQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        queues.remove(queueName);
    }

    public static PlayerQueue getPlayerQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        return (PlayerQueue) queues.get(queueName);
    }

    public static AbstractQueue<?> getQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        return queues.get(queueName);
    }
}
