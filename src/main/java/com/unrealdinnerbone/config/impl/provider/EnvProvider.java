package com.unrealdinnerbone.config.impl.provider;

import com.google.gson.JsonElement;
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
                String envKey = getEnvKey(parent, category1.getId());
                handleCategory(envKey, category1);
            }else {
                String name = getEnvKey(parent, configValue.getId());
                String string = System.getenv(name);
                if(string != null) {
                    JsonElement element = configValue.createElement(string);
                    if(element.isJsonObject()) {
                        configValue.fromJsonElement(gson, element.getAsJsonObject());
                    }else {
                        configValue.fromJsonElement(gson, element);
                    }

                }
            }
        }
    }

    public String getEnvKey(String parent, String key) {
        return parent == null || parent.isEmpty() ? key : parent + "_" + key;
    }

}
