package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.ID;
import com.unrealdinnerbone.config.impl.provider.EnvProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ConfigManager {

    private static final List<ConfigManager> managers = new ArrayList<>();
    private final List<ConfigValue<?>> configs = new ArrayList<>();
    private final IProvider provider;


    public ConfigManager(IProvider provider) {
        this.provider = provider;
        managers.add(this);
    }

    public <T, A> A loadConfig(String id, Function<IConfigCreator, A> configFunction) {
        return configFunction.apply(new IConfigCreator() {
            @Override
            public <D, R extends ConfigValue<D>> R create(String key, D defaultValue, ConfigCreator<D, R> function) {
                R configValue = function.apply(ID.of(id, key), provider, defaultValue);
                configs.add(configValue);
                return configValue;
            }
        });
    }

    public List<ConfigValue<?>> getConfigs() {
        return configs;
    }

    public static List<ConfigValue<?>> getAllConfigs() {
        return managers.stream().map(ConfigManager::getConfigs).flatMap(Collection::stream).toList();
    }


    public static Optional<ConfigValue<?>> findConfig(ID id) {
        return getAllConfigs().stream().filter(config -> config.getId().is(id)).findFirst();
    }

    public static ConfigManager createSimpleEnvPropertyConfigManger() {
        return new ConfigManager(EnvProvider.ENV_PROVIDER);
    }


}