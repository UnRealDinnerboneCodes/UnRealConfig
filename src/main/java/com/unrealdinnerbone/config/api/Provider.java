package com.unrealdinnerbone.config.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;

import java.util.ArrayList;
import java.util.function.Function;

public abstract class Provider {

    protected final ConfigCategory configCategory;
    protected final Gson gson;
    public Provider(Gson gson) {
        this.configCategory = new ConfigCategory("");
        this.gson = gson;
    }

    public Provider() {
        this(new GsonBuilder().setPrettyPrinting().serializeNulls().create());
    }

    public Gson getGson() {
        return gson;
    }

    public ConfigCategory getConfigCategory() {
        return configCategory;
    }

    public <T> T loadConfig(Function<ConfigCreator, T> configFunction) {
        return configCategory.addFromClass(configFunction);
    }

    public <T> T loadConfig(String name, Function<ConfigCreator, T> configFunction) {
        return configCategory.addFromClass(name, configFunction);
    }

    public abstract void read() throws ConfigException;

    public boolean save() throws ConfigException {
        return false;
    }


}
