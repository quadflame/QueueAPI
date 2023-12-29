package com.quadflame.queueapi;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuppressWarnings("unused")
public abstract class AbstractQueue<T> {
    private final List<T> players = new ArrayList<>();

    public abstract String getName();

    public abstract int getTeamSize();

    public abstract int getTeamCount();

    public void join(T t) {
        if(isFull()) return;
        players.add(t);
        onJoin(t);
        if(isFull()) {
            List<Team<T>> teams = new ArrayList<>();
            for (int i = 0; i < getTeamCount(); i++) {
                teams.add(new Team<>());
            }
            for (int i = 0; i < getTeamSize() * getTeamCount(); i++) {
                T queueT = players.get(i);
                teams.get(i % getTeamCount()).getMembers().add(queueT);
            }
            players.clear();
            onFill(teams);
        }
    }

    public abstract void onJoin(T t);

    public void remove(T t) {
        players.remove(t);
        onLeave(t);
    }

    public abstract void onLeave(T t);

    public boolean isFull() {
        return players.size() == getTeamSize() * getTeamCount();
    }

    public abstract void onFill(List<Team<T>> teams);

    public static class Builder<T> {
        private String name;
        private int teamSize;
        private int teamCount;
        private QueueAction<T> queueAction;

        public Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<T> teamSize(int teamSize) {
            this.teamSize = teamSize;
            return this;
        }

        public Builder<T> teamCount(int teamCount) {
            this.teamCount = teamCount;
            return this;
        }

        public Builder<T> queueAction(QueueAction<T> queueAction) {
            this.queueAction = queueAction;
            return this;
        }

        public AbstractQueue<T> build() {
            if (name == null) throw new IllegalArgumentException("Name cannot be null");
            if (teamSize <= 0) throw new IllegalArgumentException("Team size cannot be 0");
            if (teamCount <= 0) throw new IllegalArgumentException("Team count cannot be 0");
            if (queueAction == null) throw new IllegalArgumentException("Queue action cannot be null");
            return new AbstractQueue<T>() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public int getTeamSize() {
                    return teamSize;
                }

                @Override
                public int getTeamCount() {
                    return teamCount;
                }

                @Override
                public void onJoin(T t) {
                    queueAction.onJoin(t);
                }

                @Override
                public void onLeave(T t) {
                    queueAction.onLeave(t);
                }

                @Override
                public void onFill(List<Team<T>> teams) {
                    queueAction.onFill(teams);
                }
            };
        }
    }
}
