package com.quadflame.queueapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PlayerQueue extends AbstractQueue<Player> {
    private final String name;
    private final int teamSize;
    private final int teamCount;
    private final QueueAction<Player> queueAction;

    @Override
    public void onJoin(Player player) {
        queueAction.onJoin(player);
    }

    @Override
    public void onLeave(Player player) {
        queueAction.onLeave(player);
    }

    @Override
    public void onFill(List<Team<Player>> teams) {
        queueAction.onFill(teams);
    }
}
