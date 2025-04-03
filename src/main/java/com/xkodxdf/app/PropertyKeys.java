package com.xkodxdf.app;

public enum PropertyKeys {

    DB_URL("db.url"),
    DB_USER("db.user"),
    DB_PASSWORD("db.password"),
    DB_POOL_SIZE("db.pool_size");

    private final String key;

    public String getKey() {
        return key;
    }

    PropertyKeys(String key) {
        this.key = key;
    }
}
