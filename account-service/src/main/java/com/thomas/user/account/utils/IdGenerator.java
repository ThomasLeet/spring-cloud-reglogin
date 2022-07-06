package com.thomas.user.account.utils;


import org.apache.commons.lang.math.JVMRandom;
import xyz.downgoon.snowflake.Snowflake;

public enum IdGenerator {
    INSTANCE;

    private IdGenerator() {
        this.snowflake = new Snowflake(1,JVMRandom.nextLong(29)+1);
    }

    Snowflake snowflake;
    public static long getUid(){
        return INSTANCE.snowflake.nextId();
    }
}
