package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.provider.IProvider;

import java.util.Optional;

public class EnvProvider implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider();

    @Override
    public Optional<Object> get(String storeLocation, String configName, String value) {
        return Optional.ofNullable(System.getenv().getOrDefault(storeLocation + "." + configName + "." + value, null));
    }
}
