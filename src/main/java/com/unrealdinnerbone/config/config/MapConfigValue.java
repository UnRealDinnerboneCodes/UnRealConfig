package com.unrealdinnerbone.config.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.unrealdinnerbone.config.api.Provider;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MapConfigValue<K, V> extends TypedConfigValue<Map<K, V>> {

    public MapConfigValue(Provider provider, String id, @Nullable Map<K, V> defaultValue, Class<K> kClass, Class<V> vClass) {
        super(provider, id, defaultValue, TypeToken.getParameterized(Map.class, kClass, vClass).getType());
    }

    @Override
    public JsonElement createElement(String string) {
        return string == null ? JsonNull.INSTANCE : JsonParser.parseString("{" + string + "}").getAsJsonObject();
    }

    public void put(K key, V value, boolean save) {
        Map<K, V> currentValue = get();
        if (currentValue != null) {
            currentValue.put(key, value);
            if (save) {
                trySave();
            }
        }
    }

    public void remove(K key, boolean save) {
        Map<K, V> currentValue = get();
        if (currentValue != null) {
            if (currentValue.remove(key) != null) {
                if (save) {
                    trySave();
                }
            }
        }
    }
}
