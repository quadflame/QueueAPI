package com.quadflame.queueapi;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class QueueAPI {
    private static final Map<String, Queue> queues = new HashMap<>();

    public static void createQueue(Queue queue) {
        if(queues.containsKey(queue.getName())) throw new IllegalArgumentException("Queue with name " + queue.getName() + " already exists!");
        queues.put(queue.getName(), queue);
    }

    public static void deleteQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        queues.remove(queueName);
    }

    public static Queue getQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        return queues.get(queueName);
    }

    public static void joinQueue(String queueName, Player player) {
        getQueue(queueName).join(player);
    }

    public static void leaveQueue(String queueName, Player player) {
        getQueue(queueName).remove(player);
    }
}
