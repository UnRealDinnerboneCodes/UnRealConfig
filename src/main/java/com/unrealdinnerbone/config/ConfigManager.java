package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.ID;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.impl.BasicCreator;
import com.unrealdinnerbone.config.impl.provider.EnvProvider;

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

    public <T> T loadConfig(String id, Function<IConfigCreator, T> configFunction) {
        return loadConfigFromCreator(id, BasicCreator::new, configFunction);
    }

    public <T> T loadConfigFromCreator(String id, ICreator creator, Function<IConfigCreator, T> configFunction) {
        return configFunction.apply(creator.create(id, provider, configs::add));

    }

    public Set<ConfigValue<?>> getConfigs() {
        return configs;
    }

    public static List<ConfigValue<?>> getAllConfigs() {
        return managers.stream().map(ConfigManager::getConfigs).flatMap(Collection::stream).toList();
    }


    public static Optional<ConfigValue<?>> findConfig(ID id) {
        return getAllConfigs().stream()
                .filter(config -> config.getId().is(id))
                .findFirst();
    }

    public static ConfigManager createSimpleEnvPropertyConfigManger() {
        return new ConfigManager(EnvProvider.ENV_PROVIDER);
    }


}