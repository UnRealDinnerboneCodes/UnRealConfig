package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.IProvider;

import java.util.Optional;
import java.util.function.Function;

public record TestProvider(Function<String, Object> function) implements IProvider {

    @Override
    public Optional<Object> get(String storeLocation, String configName, String value) {
        return Optional.ofNullable(function.apply(storeLocation + "." + configName + "." + value));
    }
}
