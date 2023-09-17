package com.unrealdinnerbone.config.exception;

import org.jetbrains.annotations.Nullable;

public class CachedConfigValue<T> {

    private final ConfigGetter<T> cachedGetter;
    private T currentValue;

    public CachedConfigValue(ConfigGetter<@Nullable T> cachedGetter) {
        this.cachedGetter = cachedGetter;
    }


    public T get() throws ConfigException {
        if(currentValue == null) {
            currentValue = cachedGetter.get();
        }
        return currentValue;
    }

    public boolean isPresent() {
        return currentValue != null;
    }

    public void set(T value) {
        currentValue = value;
    }

    public void invalidate() {
        currentValue = null;
    }

}
