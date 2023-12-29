package com.quadflame.queueapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PlayerQueue extends AbstractQueue<Player> {
    private final String name;
    private final int teamSize;
    private final int teamCount;
    private final QueueAction queueAction;

    @Override
    public void onJoin(Player player) {
        queueAction.onJoin(player);
    }

    @Override
    public void onLeave(Player player) {
        queueAction.onLeave(player);
    }

    @Override
    public void onFill(List<AbstractQueue<Player>.Team> teams) {
        queueAction.onFill(teams);
    }

    @Getter
    public static class Team {
        private final List<Player> players = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private String name;
        private int teamSize;
        private int teamCount;
        private QueueAction queueAction;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder teamSize(int teamSize) {
            this.teamSize = teamSize;
            return this;
        }

        public Builder teamCount(int teamCount) {
            this.teamCount = teamCount;
            return this;
        }

        public Builder queueAction(QueueAction queueAction) {
            this.queueAction = queueAction;
            return this;
        }

        public PlayerQueue build() {
            if (name == null) throw new IllegalArgumentException("Name cannot be null");
            if (teamSize <= 0) throw new IllegalArgumentException("Team size cannot be 0");
            if (teamCount <= 0) throw new IllegalArgumentException("Team count cannot be 0");
            if (queueAction == null) throw new IllegalArgumentException("Queue action cannot be null");
            return new PlayerQueue(name, teamSize, teamCount, queueAction);
        }
    }
}
