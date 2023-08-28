package com.unrealdinnerbone.config.api;

import com.unrealdinnerbone.config.exception.CachedConfigValue;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class ConfigValue<T> {
    private final Namespace id;
    @Nullable
    private final T defaultValue;
    protected final IProvider provider;
    protected final CachedConfigValue<T> activeValue;

    public ConfigValue(Namespace id, IProvider provider, @Nullable T defaultValue) {
        this.id = id;
        this.provider = provider;
        this.defaultValue = defaultValue;
        this.activeValue = new CachedConfigValue<>(() -> provider.get(id, getClassType(), this::from));
    }


    public <B> void set(Class<B> clazz, B value) throws ConfigParseException {
        set(clazz.isInstance(getClassType()) ? getClassType().cast(value) : from(clazz, value));
    }

    @NotNull
    public abstract <B> T from(Class<B> clazz, B value) throws ConfigParseException;

    public Namespace getId() {
        return id;
    }

    public Optional<T> get() {
        try {
            return Optional.of(getExceptionally());
        } catch (ConfigException e) {
            return Optional.empty();
        }
    }

    public T getExceptionally() throws ConfigException {
        return activeValue.get();
    }

    @Nullable
    public T getOrDefault() {
        return get().orElse(defaultValue);
    }

    public List<String> getExamples() {
        return Collections.emptyList();
    }

    @Nullable
    public T getDefaultValue() {
        return defaultValue;
    }

    public void invalidate() {
        activeValue.invalidate();
    }
    public boolean set(T value) throws ConfigParseException {
        activeValue.invalidate();
        return provider.save(id, getClassType(), value);
    }

    public abstract Class<T> getClassType();

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
