package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.unrealdinnerbone.config.api.exception.ConfigParseException;

public abstract class NumberConfig<T extends Number> extends ConfigValue<T> {

    public NumberConfig(String id, T defaultValue) {
        super(id, defaultValue);
    }

    @Override
    protected T serialize(Gson gson, JsonElement jsonElement) throws UnsupportedOperationException, IllegalStateException {
        return numberValue(jsonElement.getAsNumber());
    }

    @Override
    protected JsonElement deserialize(Gson gson, T value) throws ConfigParseException {
        return new JsonPrimitive(value);
    }

    public abstract T numberValue(Number number);


}
