package com.unrealdinnerbone.config.config;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public abstract class TypedConfigValue<T> extends ConfigValue<T> {

    protected final Type type;

    public TypedConfigValue(String id, @Nullable T defaultValue) {
        super(id, defaultValue);
        this.type = new TypeToken<T>(){}.getType();
    }

    public Type getType() {
        return type;
    }
}
