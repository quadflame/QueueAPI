package com.quadflame.queueapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Queue {
    private final List<Player> players = new ArrayList<>();
    private final String name;
    private final int teamSize;
    private final int teamCount;
    private final QueueAction queueAction;

    public void join(Player player) {
        if(isFull()) return;
        players.add(player);
        queueAction.onJoin(player);
        if(isFull()) {
            List<Team> teams = new ArrayList<>();
            for (int i = 0; i < teamCount; i++) {
                teams.add(new Team());
            }
            for (int i = 0; i < teamSize * teamCount; i++) {
                Player queuePlayer = players.get(i);
                teams.get(i % teamCount).getPlayers().add(queuePlayer);
            }
            queueAction.onFill(teams);
        }
    }

    public void remove(Player player) {
        players.remove(player);
        queueAction.onLeave(player);
    }

    public boolean isFull() {
        return players.size() == teamSize * teamCount;
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

        public Queue build() {
            if (name == null) throw new IllegalArgumentException("Name cannot be null");
            if (teamSize <= 0) throw new IllegalArgumentException("Team size cannot be 0");
            if (teamCount <= 0) throw new IllegalArgumentException("Team count cannot be 0");
            if (queueAction == null) throw new IllegalArgumentException("Queue action cannot be null");
            return new Queue(name, teamSize, teamCount, queueAction);
        }
    }
}
