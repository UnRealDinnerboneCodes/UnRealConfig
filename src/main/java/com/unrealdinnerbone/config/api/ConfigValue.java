package com.unrealdinnerbone.config.api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ConfigValue<T> {

    private final ID id;
    private final T defaultValue;
    protected final IProvider provider;

    public ConfigValue(ID id, IProvider provider, T defaultValue) {
        this.id = id;
        this.provider = provider;
        this.defaultValue = defaultValue;
        provider.setDefault(id, defaultValue);
        set(defaultValue);
    }

    public boolean setValue(T value) {
        return save(value);
    }

    public <O> void set(O value) throws IllegalArgumentException {
        setValue(fromObject(value));
    }

    @NotNull
    public abstract <O> T fromObject(O o) throws IllegalArgumentException;

    public ID getId() {
        return id;
    }

    public T getValue() {
        return provider.get(id, defaultValue).map(this::fromObject).orElse(null);
    }

    public List<String> getExampleValues() {
        List<String> list = new ArrayList<>(getExamples());
        list.add(getDefaultValue().toString());
        return list;
    }

    public List<String> getExamples() {
        return Collections.emptyList();
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean save(T t) {
        return provider.save(id, t);
    }

    public abstract Class<T> getClassType();
}
