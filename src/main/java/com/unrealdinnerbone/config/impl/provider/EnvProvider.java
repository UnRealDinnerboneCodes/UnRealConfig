package com.unrealdinnerbone.config.impl.provider;

import com.google.gson.JsonPrimitive;
import com.unrealdinnerbone.config.api.ConfigCreator;
import com.unrealdinnerbone.config.api.Provider;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.api.exception.ConfigParseException;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;

import java.util.function.Function;

public class EnvProvider<T> extends Provider {

    @Override
    public void read() throws ConfigException {
        handleCategory("", configCategory);
    }

    public void handleCategory(String parent, ConfigCategory configCategory) throws ConfigParseException {
        for (ConfigValue<?> configValue : configCategory.get()) {
            if (configValue instanceof ConfigCategory category1) {
                handleCategory(parent + configCategory.getId() + "_", category1);
            }else {
                String string = System.getenv(parent + configValue.getId());
                configValue.fromJsonElement(gson, new JsonPrimitive(string).getAsJsonObject());
            }
        }
    }

}
