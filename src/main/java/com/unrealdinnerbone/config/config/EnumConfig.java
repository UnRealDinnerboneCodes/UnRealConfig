package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Arrays;
import java.util.List;

public class EnumConfig<T extends Enum<T>> extends ConfigValue<T> {

    private final Class<T> type;
    private final List<String> examples;

    public EnumConfig(String id, T defaultValue, Class<T> clazz) {
        super(id, defaultValue);
        this.type = clazz;
        this.examples = Arrays.stream(clazz.getEnumConstants()).map(Enum::name).toList();
    }

    @Override
    public T serialize(Gson gson, JsonElement jsonElement) throws UnsupportedOperationException, IllegalStateException {
        return gson.fromJson(jsonElement, type);
    }

    @Override
    public JsonElement deserialize(Gson gson, T value) {
        return gson.toJsonTree(value);
    }


    @Override
    public List<String> getExamples() {
        return examples;
    }

}
