package com.unrealdinnerbone.config.api;

public record ID(String key, String value) {
    @Override
    public String toString() {
        return key + ":" + value;
    }

    public static ID of(String key, String value) {
        return new ID(key, value);
    }

    public boolean is(String key, String value) {
        return toString().toLowerCase().equals(key.toLowerCase() + ":" + value.toLowerCase());
    }

    public boolean is(ID id) {
        return toString().equalsIgnoreCase(id.toString());
    }

    public String toString(String split) {
        return key + split + value;
    }
}
