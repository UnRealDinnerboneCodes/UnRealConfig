package com.unrealdinnerbone.config.api;

import com.unrealdinnerbone.unreallib.CachedValue;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ConfigValue<T> {

    private final Namespace id;
    private final T defaultValue;
    protected final IProvider provider;
    private final CachedValue<T> activeValue;
    private final CachedValue<List<String>> examples;

    public ConfigValue(Namespace id, IProvider provider, T defaultValue) {
        this.id = id;
        this.provider = provider;
        this.defaultValue = defaultValue;
        this.activeValue = new CachedValue<>(() -> provider.get(id)
                .map(this::fromObject)
                .orElse(defaultValue));
        this.examples = new CachedValue<>(() -> {
            List<String> list = new ArrayList<>(getExamples());
            String theDefaultValue = getDefaultValue().toString();
            String theActiveValue = activeValue.get().toString();
            list.add(theDefaultValue);
            if(!theActiveValue.equals(theDefaultValue)) {
                list.add(theActiveValue);
            }
            return list;
        });
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

    public Namespace getId() {
        return id;
    }

    public T getValue() {
        return activeValue.get();
    }

    public List<String> getExampleValues() {
        return examples.get();
    }

    public List<String> getExamples() {
        return Collections.emptyList();
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    private boolean save(T t) {
        activeValue.invalidate();
        examples.invalidate();
        return provider.save(id, t);
    }

    public abstract Class<T> getClassType();

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
