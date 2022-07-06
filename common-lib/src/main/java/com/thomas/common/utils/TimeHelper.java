package com.thomas.common.utils;

public class TimeHelper {
    public static final int currentTimeSeconds() {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    public static long get10MinutesExpireTime() {
        return System.currentTimeMillis() + 600*1000;
    }
}
