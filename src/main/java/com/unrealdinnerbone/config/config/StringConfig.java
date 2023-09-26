package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class StringConfig extends ConfigValue<String> {

    public StringConfig(String id, String defaultValue) {
        super(id, defaultValue);
    }

    @Override
    public String serialize(Gson gson, JsonElement jsonElement) throws UnsupportedOperationException, IllegalStateException {
        return jsonElement.getAsString();
    }

    @Override
    public JsonElement deserialize(Gson gson, String value) {
        return new JsonPrimitive(value);
    }
}
