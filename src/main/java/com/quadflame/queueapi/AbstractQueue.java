package com.quadflame.queueapi;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuppressWarnings("unused")
public abstract class AbstractQueue<T> {
    private final List<T> queue = new ArrayList<>();

    public abstract String getName();

    public abstract int getTeamSize();

    public abstract int getTeamCount();

    public boolean join(T t) {
        if(isFull() || queue.contains(t)) return false;
        queue.add(t);
        onJoin(t);
        if(isFull()) {
            List<Team<T>> teams = new ArrayList<>();
            for (int i = 0; i < getTeamCount(); i++) {
                teams.add(new Team<>());
            }
            for (int i = 0; i < getTeamSize() * getTeamCount(); i++) {
                T queueT = queue.get(i);
                teams.get(i % getTeamCount()).add(queueT);
            }
            queue.clear();
            onFill(teams);
        }
        return true;
    }

    public abstract void onJoin(T t);

    public boolean remove(T t) {
        if(!queue.contains(t)) return false;
        queue.remove(t);
        onLeave(t);
        return true;
    }

    public abstract void onLeave(T t);

    public boolean isFull() {
        return queue.size() == getTeamSize() * getTeamCount();
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
            Preconditions.checkNotNull(name, "Name cannot be null");
            Preconditions.checkArgument(teamSize > 0, "Team size cannot be <= 0");
            Preconditions.checkArgument(teamCount > 0, "Team count cannot be <= 0");
            Preconditions.checkNotNull(queueAction, "Queue action cannot be null");
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
