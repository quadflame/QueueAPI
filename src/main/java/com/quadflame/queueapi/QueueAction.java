package com.quadflame.queueapi;

import java.util.List;

public interface QueueAction<T> {

    void onJoin(T t);

    void onLeave(T t);

    void onFill(List<Team<T>> teams);

}
