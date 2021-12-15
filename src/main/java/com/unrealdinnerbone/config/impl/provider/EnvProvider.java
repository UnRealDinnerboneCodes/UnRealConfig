package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.ID;

import java.util.Optional;

public class EnvProvider implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider();

    @Override
    public <T> Optional<Object> get(ID id, T defaultValue) {
        return Optional.ofNullable(System.getenv().getOrDefault(id.toString().replace(".",":"), defaultValue.toString()));
    }
}
