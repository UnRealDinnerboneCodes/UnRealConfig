package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.impl.provider.EnvProvider;

import java.util.function.Function;

public record ConfigManager(IProvider provider) {

    public <T> T loadConfig(Function<IProvider, T> configFunction) {
        return configFunction.apply(provider);
    }

    public static ConfigManager createSimpleEnvPropertyConfigManger() {
        return new ConfigManager(EnvProvider.ENV_PROVIDER);
    }
}