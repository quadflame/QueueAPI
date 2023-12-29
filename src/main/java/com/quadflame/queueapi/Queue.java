package com.quadflame.queueapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Queue {
    private final List<Player> queue = new ArrayList<>();
    private final String name;
    private final int teamSize;
    private final int teamCount;
    private final QueueAction queueAction;
    private Status status = Status.QUEUING;

    public void join(Player player) {
        if(status.equals(Status.FULL)) return;
        queue.add(player);
        queueAction.onJoin(player);
        if(queue.size() == teamSize * teamCount) {
            status = Status.FULL;
            List<Team> teams = new ArrayList<>();
            for (int i = 0; i < teamCount; i++) {
                teams.add(new Team());
            }
            for (int i = 0; i < teamSize * teamCount; i++) {
                Player queuePlayer = queue.get(i);
                teams.get(i % teamCount).getPlayers().add(queuePlayer);
            }
            queueAction.onFill(teams);
        }
    }

    public void remove(Player player) {
        queue.remove(player);
        queueAction.onLeave(player);
        if(status.equals(Status.FULL)) {
            status = Status.QUEUING;
        }
    }

    public enum Status {
        QUEUING,
        FULL
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
