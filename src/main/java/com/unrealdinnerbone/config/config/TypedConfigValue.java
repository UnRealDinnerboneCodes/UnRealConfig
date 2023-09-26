package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.unrealdinnerbone.config.api.exception.ConfigParseException;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class TypedConfigValue<T> extends ConfigValue<T> {

    private final Type type;

    public TypedConfigValue(String id, @Nullable T defaultValue, Type type) {
        super(id, defaultValue);
        this.type = type;
    }

    public TypedConfigValue(String id, @Nullable T defaultValue, Class<T> type) {
        super(id, defaultValue);
        this.type = TypeToken.get(type).getType();
    }

    @Override
    protected T serialize(Gson gson, JsonElement jsonElement) {
        return gson.fromJson(jsonElement, type);
    }

    @Override
    protected JsonElement deserialize(Gson gson, T value) {
        return gson.toJsonTree(value);
    }

    public Type getType() {
        return type;
    }
}
