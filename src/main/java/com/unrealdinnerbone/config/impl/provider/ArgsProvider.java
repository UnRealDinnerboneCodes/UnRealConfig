package com.unrealdinnerbone.config.impl.provider;

import com.google.gson.JsonPrimitive;
import com.unrealdinnerbone.config.api.ConfigCreator;
import com.unrealdinnerbone.config.api.Provider;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.api.exception.ConfigParseException;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ArgsProvider extends Provider {
    private final Map<String, String> args = new HashMap<>();

    public ArgsProvider(String[] args) {
        super();
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String[] split = arg.split("=");
                if (split.length == 2) {
                    this.args.put(split[0].substring(2), split[1]);
                } else {
                    this.args.put(split[0].substring(2), "true");
                }
            }
        }
    }

    @Override
    public void read() throws ConfigException {
        handleCategory("", configCategory);
    }

    public void handleCategory(String parent, ConfigCategory configCategory) throws ConfigParseException {
        for (ConfigValue<?> configValue : configCategory.get()) {
            if (configValue instanceof ConfigCategory category1) {
                handleCategory(parent + configCategory.getId() + "_", category1);
            }else {
                String string = args.get(configValue.getId());
                if(string == null) {
                    configValue.setValue(null);
                }else {
                    configValue.fromJsonElement(gson, new JsonPrimitive(string));
                }
            }
        }
    }
}
