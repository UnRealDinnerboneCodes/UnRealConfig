package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListConfig<T> extends TypedConfigValue<List<T>> {


    public ListConfig(String id, @Nullable List<T> defaultValue) {
        super(id, defaultValue);
    }

    @Override
    public List<T> serialize(Gson gson, JsonElement jsonElement) throws UnsupportedOperationException, IllegalStateException {
        return gson.fromJson(jsonElement.getAsJsonArray(), type);
    }

    @Override
    public JsonElement deserialize(Gson gson, List<T> value) {
        JsonArray jsonArray = new JsonArray();
        for (T configValue : value) {
            JsonElement jsonTree = gson.toJsonTree(configValue);
            jsonArray.add(jsonTree);
        }
        return jsonArray;
    }

}
