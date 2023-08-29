package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.impl.provider.EnvProvider;
import com.unrealdinnerbone.unreallib.Namespace;

import java.util.*;
import java.util.function.Function;

public class ConfigManager {

    private static final List<ConfigManager> managers = new ArrayList<>();
    private final Set<ConfigValue<?>> configs = new LinkedHashSet<>();
    private final IProvider provider;

    public ConfigManager(IProvider provider) {
        this.provider = provider;
        managers.add(this);

    }
    public <T> T loadConfig(String id, Function<ConfigCreator, T> configFunction) {
        return configFunction.apply(new ConfigCreator(id, provider, configs::add));
    }

    public void invalidateProvider() {
        provider.invalidateCache();
    }

    public void invalidateConfigs() {
        configs.forEach(ConfigValue::invalidate);
    }

    public void invalidate() {
        invalidateProvider();
        invalidateConfigs();
    }

    public Set<ConfigValue<?>> getConfigs() {
        return configs;
    }

    public static List<ConfigValue<?>> getAllConfigs() {
        return managers.stream().map(ConfigManager::getConfigs).flatMap(Collection::stream).toList();
    }


    public static Optional<ConfigValue<?>> findConfig(Namespace id) {
        return getAllConfigs().stream()
                .filter(config -> config.getId().is(id))
                .findFirst();
    }

    public static ConfigManager createSimpleEnvPropertyConfigManger() {
        return new ConfigManager(EnvProvider.ENV_PROVIDER);
    }


}