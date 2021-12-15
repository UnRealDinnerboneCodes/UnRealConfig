package com.unrealdinnerbone.config.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class ConfigValue<T> {

    private final Namespace id;
    private final T defaultValue;
    protected final IProvider provider;

    public ConfigValue(Namespace id, IProvider provider, T defaultValue) {
        this.id = id;
        this.provider = provider;
        this.defaultValue = defaultValue;
        provider.setDefault(id, defaultValue);
        set(defaultValue);
    }

    public void setValue(T value) {
        save(value);
    }


    public <O> void set(O value) throws IllegalArgumentException {
        setValue(fromObject(value));
    }

    @NotNull
    public abstract <O> T fromObject(O o) throws IllegalArgumentException;

    public Namespace getId() {
        return id;
    }

    public T getValue() {
        return provider.get(id, defaultValue).map(this::fromObject).orElse(null);
    }

    public List<String> getExamples() {
        return Collections.emptyList();
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void save(T t) {
        provider.save(id, t);
    }
}
