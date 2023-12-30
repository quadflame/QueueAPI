package com.quadflame.queueapi;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;

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

    public static boolean isPlayerInQueue(Player player) {
        for (PlayerQueue playerQueue : getPlayerQueues()) {
            if(playerQueue.getQueue().contains(player)) return true;
        }
        return false;
    }

    public static AbstractQueue<?> getQueue(String queueName) {
        if(!queues.containsKey(queueName)) throw new IllegalArgumentException("Queue with name " + queueName + " does not exist!");
        return queues.get(queueName);
    }

    public static Set<AbstractQueue<?>> getQueues() {
        return Sets.newHashSet(queues.values());
    }

    public static boolean isInQueue(Object object) {
        for (AbstractQueue<?> queue : getQueues()) {
            if(queue.getQueue().contains(object)) return true;
        }
        return false;
    }
}
