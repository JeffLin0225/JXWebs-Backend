package com.blog.Config;

public enum RedisSetting {
    TTL_300S(300),
    TTL_DEFAULT(600),
    DB_DEFAULT(0),
    DB_ONE(1);

    private final int values;
    RedisSetting(int values) {
        this.values = values;
    }

    public int getValues() {
        return values;
    }
}
