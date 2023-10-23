package com.github.korbeckik.common.utils;

public class StopWatch {

    private final Long startMillis;

    private StopWatch() {
        this.startMillis = System.currentTimeMillis();
    }

    public static StopWatch start() {
        return new StopWatch();
    }

    public Long stop() {
        return System.currentTimeMillis() - startMillis;
    }
}
