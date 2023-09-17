package com.unrealdinnerbone.config.api;

import com.unrealdinnerbone.config.exception.CachedConfigValue;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class ConfigValue<T> implements ClassMapper<T> {
    private final ConfigID id;
    @Nullable
    private final T defaultValue;
    protected final IProvider provider;
    protected final CachedConfigValue<T> activeValue;

    public ConfigValue(ConfigID id, IProvider provider, @Nullable T defaultValue) {
        this.id = id;
        this.provider = provider;
        this.defaultValue = defaultValue;
        this.activeValue = new CachedConfigValue<>(() -> provider.get(id, getClassType(), this));
    }


    public <B> void set(Class<B> clazz, B value) throws ConfigParseException {
        set(map(clazz, value));
    }

    @Override
    public final <C> T map(Class<C> clazz, C value) throws ConfigParseException {
        if (clazz.isInstance(getClassType()) || getClassType() == clazz) {
            return getClassType().cast(value);
        }else {
            return from(clazz, value);
        }
    }

    @NotNull
    protected abstract <B> T from(Class<B> clazz, B value) throws ConfigParseException;

    public ConfigID getId() {
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
    public void set(T value) throws ConfigParseException {
        activeValue.set(value);
    }

    public boolean save() throws ConfigException {
        if(activeValue.isPresent()) {
            return provider.save(id, getClassType(), activeValue.get());
        }
        return false;
    }

    public abstract Class<T> getClassType();

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
