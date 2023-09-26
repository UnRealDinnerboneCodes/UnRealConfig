package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MapConfig<T, V> extends TypedConfigValue<Map<T, V>> {

    public MapConfig(String id, @Nullable Map<T, V> defaultValue) {
        super(id, defaultValue);
    }

    @Override
    public Map<T, V> serialize(Gson gson, JsonElement jsonElement) throws UnsupportedOperationException, IllegalStateException {
        return gson.fromJson(jsonElement, type);
    }

    @Override
    public JsonElement deserialize(Gson gson, Map<T, V> value) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<T, V> entry : value.entrySet()) {
            JsonElement jsonTree = gson.toJsonTree(entry.getValue());
            jsonObject.add(String.valueOf(entry.getKey()), jsonTree);
        }
        return jsonObject;
    }
}
