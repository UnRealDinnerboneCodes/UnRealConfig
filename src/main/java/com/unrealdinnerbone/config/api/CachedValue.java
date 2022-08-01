package com.unrealdinnerbone.config.api;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CachedValue<T> {

    private final Supplier<T> cachedGetter;
    private T currentValue;

    public CachedValue(Supplier<@NotNull T> cachedGetter) {
        this.cachedGetter = cachedGetter;
    }


    public T get() {
        if(currentValue == null) {
            currentValue = cachedGetter.get();
        }
        return currentValue;
    }

    public void invalidate() {
        currentValue = null;
    }

}
