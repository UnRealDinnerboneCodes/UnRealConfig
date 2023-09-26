package com.unrealdinnerbone.config.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;

import java.util.ArrayList;
import java.util.function.Function;

public abstract class Provider<T> {

    protected final ConfigCategory configCategory;
    protected final Gson gson;
    private final T configInstance;

    public Provider(Gson gson, Function<ConfigCreator, T> configFunction) {
        this.configCategory = new ConfigCategory("", new ArrayList<>());
        this.configInstance = configFunction.apply(new ConfigCreator(this));
        this.gson = gson;
    }

    public Provider(Function<ConfigCreator, T> configFunction) {
        this(new GsonBuilder().setPrettyPrinting().serializeNulls().create(), configFunction);
    }

    public Gson getGson() {
        return gson;
    }

    public T getConfig() {
        return configInstance;
    }

    public ConfigCategory getConfigCategory() {
        return configCategory;
    }

    public void addConfigValue(ConfigValue<?> configValue) {
        configCategory.addConfigValue(configValue);
    }

    public abstract void read() throws ConfigException;

    public boolean save() throws ConfigException {
        return false;
    }


}
