package com.unrealdinnerbone.config.exception;

import com.unrealdinnerbone.unreallib.exception.ExceptionSuppler;
import org.jetbrains.annotations.Nullable;

public class CachedConfigValue<T> implements ExceptionSuppler<T, ConfigException> {

    private final ExceptionSuppler<T, ConfigException> cachedGetter;
    private T currentValue;

    public CachedConfigValue(ExceptionSuppler<@Nullable T, ConfigException> cachedGetter) {
        this.cachedGetter = cachedGetter;
    }

    @Override
    public T get() throws ConfigException {
        if(currentValue == null) {
            currentValue = cachedGetter.get();
        }
        return currentValue;
    }

    public void invalidate() {
        currentValue = null;
    }

}
