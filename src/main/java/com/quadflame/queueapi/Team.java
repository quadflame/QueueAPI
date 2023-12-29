package com.quadflame.queueapi;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Team {
    private final List<Player> players = new ArrayList<>();

}
