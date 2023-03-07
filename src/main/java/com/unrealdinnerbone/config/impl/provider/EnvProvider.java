package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.unreallib.Namespace;

import java.util.Optional;

public record EnvProvider(String split) implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider("_");

    @Override
    public Optional<Object> get(Namespace id) {
        return Optional.ofNullable(System.getenv().getOrDefault(id.toString(split), null));
    }
}
