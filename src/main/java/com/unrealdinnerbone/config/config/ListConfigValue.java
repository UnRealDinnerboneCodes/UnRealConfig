package com.unrealdinnerbone.config.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.unrealdinnerbone.config.api.Provider;
import com.unrealdinnerbone.config.api.exception.ConfigRuntimeException;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListConfigValue<T> extends TypedConfigValue<List<T>> {

    public ListConfigValue(Provider provider, String id, @Nullable List<T> defaultValue, Class<T> clazz) {
        super(provider, id, defaultValue, TypeToken.getParameterized(List.class, clazz).getType());
    }

    @Override
    public JsonElement createElement(String string) {
        return string == null ? JsonNull.INSTANCE : JsonParser.parseString("[" + string + "]").getAsJsonArray();
    }

    public void add(T value, boolean save) {
        List<T> currentValue = get();
        if (currentValue != null) {
            currentValue.add(value);
            if (save) {
                trySave();
            }
        }
    }

    public void remove(T value, boolean save) {
        List<T> currentValue = get();
        if (currentValue != null) {
            if (currentValue.remove(value)) {
                if (save) {
                    trySave();
                }
            }
        }
    }
}
