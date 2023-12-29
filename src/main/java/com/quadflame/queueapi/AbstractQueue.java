package com.quadflame.queueapi;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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
            List<Team> teams = new ArrayList<>();
            for (int i = 0; i < getTeamCount(); i++) {
                teams.add(new Team());
            }
            for (int i = 0; i < getTeamSize() * getTeamCount(); i++) {
                T queueT = players.get(i);
                teams.get(i % getTeamCount()).getPlayers().add(queueT);
            }
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

    public abstract void onFill(List<Team> teams);

    @Getter
    public class Team {
        private final List<T> players = new ArrayList<>();
    }
}
