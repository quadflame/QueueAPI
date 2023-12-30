package com.quadflame.queueapi;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public static Set<PlayerQueue> getPlayerQueues() {
        Set<PlayerQueue> playerQueues = new HashSet<>();
        for (AbstractQueue<?> queue : queues.values()) {
            if(queue instanceof PlayerQueue)
                playerQueues.add((PlayerQueue) queue);
        }
        return playerQueues;
    }

    public static AbstractQueue<?> getQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        return queues.get(queueName);
    }

    public static Set<AbstractQueue<?>> getQueues() {
        return Sets.newHashSet(queues.values());
    }
}
