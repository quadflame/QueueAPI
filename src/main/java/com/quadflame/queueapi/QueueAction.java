package com.quadflame.queueapi;

import org.bukkit.entity.Player;

import java.util.List;

public interface QueueAction {

    void onJoin(Player player);

    void onLeave(Player player);

    void onFill(List<Queue.Team> teams);

}
