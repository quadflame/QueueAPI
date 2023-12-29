package com.quadflame.queueapi;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Team<T> {
    private final List<T> members = new ArrayList<>();
}