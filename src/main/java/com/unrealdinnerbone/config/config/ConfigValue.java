package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.unrealdinnerbone.config.api.exception.ConfigParseException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class ConfigValue<T> {

    private final String id;

    @Nullable
    private T value;

    public ConfigValue(String id, @Nullable T initialValue) {
        this.id = id;
        this.value = initialValue;
    }

    public String getId() {
        return id;
    }

    public Optional<T> find() {
        return Optional.ofNullable(value);
    }

    public T get() {
        return value;
    }

    public JsonElement createElement(String string) {
        return new JsonPrimitive(string);
    }

    public final void fromJsonElement(Gson gson, JsonElement jsonElement) throws ConfigParseException {
        try {
            this.value = serialize(gson, jsonElement);
        }catch (Exception e) {
            throw new ConfigParseException("Could not parse config value", e);
        }
    }
    
    public final JsonElement asJsonElement(Gson gson) throws ConfigParseException {
        return value == null ? JsonNull.INSTANCE : deserialize(gson, value);
    }

    public void setValue(@Nullable T value) {
        this.value = value;
    }


    @ApiStatus.OverrideOnly
    protected abstract JsonElement deserialize(Gson gson, T value) throws ConfigParseException;

    @ApiStatus.OverrideOnly
    protected abstract T serialize(Gson gson, JsonElement jsonElement) throws ConfigParseException;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
