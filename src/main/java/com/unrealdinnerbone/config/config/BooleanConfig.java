package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.Arrays;
import java.util.List;

public class BooleanConfig extends ConfigValue<Boolean> {

    private static final List<String> TYPES = Arrays.asList("true", "false");

    public BooleanConfig(String id, Boolean defaultValue) {
        super(id, defaultValue);
    }

    @Override
    protected Boolean serialize(Gson gson, JsonElement jsonElement) throws UnsupportedOperationException, IllegalStateException {
        return jsonElement.getAsBoolean();
    }

    @Override
    public JsonElement deserialize(Gson gson, Boolean value) {
        return new JsonPrimitive(value);
    }

    @Override
    public List<String> getExamples() {
        return TYPES;
    }

}
