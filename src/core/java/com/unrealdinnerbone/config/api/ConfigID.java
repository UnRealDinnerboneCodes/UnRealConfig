package com.unrealdinnerbone.config.api;

public record ConfigID(String key, String value) {

    public static ConfigID of(String key, String value) {
        return new ConfigID(key.toLowerCase(), value.toLowerCase());
    }
}