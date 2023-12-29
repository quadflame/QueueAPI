package com.quadflame.queueapi;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class QueueAPI {
    private final Map<String, Queue> queues = new HashMap<>();

    public void createQueue(Queue queue) {
        if(queues.containsKey(queue.getName())) throw new IllegalArgumentException("Queue with name " + queue.getName() + " already exists!");
        queues.put(queue.getName(), queue);
    }

    public void deleteQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        queues.remove(queueName);
    }

    public void joinQueue(String queueName, Player player) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        queues.get(queueName).join(player);
    }

    public void leaveQueue(String queueName, Player player) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        queues.get(queueName).remove(player);
    }
}
