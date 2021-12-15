package com.unrealdinnerbone.config.api;

public record Namespace(String key, String value) {
    @Override
    public String toString() {
        return key + ":" + value;
    }

    public static Namespace of(String key, String value) {
        return new Namespace(key, value);
    }

    public boolean is(String key, String value) {
        return toString().toLowerCase().equals(key.toLowerCase() + ":" + value.toLowerCase());
    }
}
