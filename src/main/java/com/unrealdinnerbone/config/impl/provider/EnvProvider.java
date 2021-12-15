package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.Namespace;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnvProvider implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider();

    @Override
    public <T> Optional<Object> get(Namespace id, T defaultValue) {
        return Optional.ofNullable(System.getenv().getOrDefault(id.toString(), defaultValue.toString()));
    }
}
