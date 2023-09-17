package com.unrealdinnerbone.config.exception;

public interface ConfigGetter<T> {
    T get() throws ConfigException;
}
