package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.unrealdinnerbone.config.api.Provider;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.api.exception.ConfigParseException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class ConfigValue<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigValue.class);

    private final String id;
    private final List<Consumer<ConfigValue<T>>> changeEvents;
    private final Provider provider;

    @Nullable
    private T value;

    public ConfigValue(Provider provider, String id, @Nullable T initialValue) {
        this.provider = provider;
        this.id = id;
        this.value = initialValue;
        this.changeEvents = new ArrayList<>();
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
        return string == null ? JsonNull.INSTANCE : new JsonPrimitive(string);
    }

    public final void fromJsonElement(Gson gson, JsonElement jsonElement) throws ConfigParseException {
        try {
            setValue(serialize(gson, jsonElement));
        }catch (Exception e) {
            throw new ConfigParseException("Could not parse config value", e);
        }
    }
    
    public final JsonElement asJsonElement(Gson gson) throws ConfigParseException {
        return value == null ? JsonNull.INSTANCE : deserialize(gson, value);
    }

    public ConfigValue<T> registerChangeHandler(Consumer<ConfigValue<T>> consumer) {
        changeEvents.add(consumer);
        return this;
    }

    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "1.5.0")
    public void setValue(@Nullable T value) {
        this.value = value;
        changeEvents.forEach(consumer -> consumer.accept(this));
    }

    public void setValue(@Nullable T value, boolean save) {
        setValue(value);
        if(save) {
            try {
                save();
            }catch (ConfigException e) {
                LOGGER.error("Could not save config", e);
            }
        }
    }

    public void ifPresent(Consumer<T> consumer) {
        if(value != null) {
            consumer.accept(value);
        }
    }

    public boolean save() throws ConfigException {
        return provider.save();
    }

    @ApiStatus.Internal
    public Provider getProvider() {
        return provider;
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
