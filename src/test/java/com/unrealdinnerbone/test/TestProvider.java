package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.Namespace;

import java.util.Optional;
import java.util.function.Function;

public record TestProvider(Function<String, Object> function) implements IProvider {

    @Override
    public <T> Optional<Object> get(Namespace id, T defaultValue) {
        return Optional.ofNullable(function.apply(id.toString()));
    }
}
