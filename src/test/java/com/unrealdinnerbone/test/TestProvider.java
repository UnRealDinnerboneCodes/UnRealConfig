package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.unreallib.Namespace;

import java.util.Optional;
import java.util.function.Function;

public record TestProvider(Function<String, Object> function) implements IProvider {

    @Override
    public Optional<Object> get(Namespace id) {
        return Optional.ofNullable(function.apply(id.toString()));
    }

}
