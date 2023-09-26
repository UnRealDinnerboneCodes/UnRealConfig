package com.unrealdinnerbone.config.api;

import com.sun.source.util.Plugin;
import com.unrealdinnerbone.config.config.*;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ConfigCreator {


    private final Provider<?> provider;
    public ConfigCreator(Provider<?> provider) {
        this.provider = provider;
    }


    public ConfigCreator createGroup(String name) {
        ConfigCategory configCategory = create(new ConfigCategory(name, new ArrayList<>()));
        return new ConfigCreator(provider) {
            @Override
            public <D, R extends ConfigValue<D>> R create(R configValue) {
                configCategory.addConfigValue(configValue);
                return configValue;
            }
        };
    }

    public <T> T createGroup(String name, Function<ConfigCreator, T> creatorFunction) {
        ConfigCreator group = createGroup(name);
        return creatorFunction.apply(group);
    }

    public <D, R extends ConfigValue<D>> R create(R configValue) {
        provider.getConfigCategory().addConfigValue(configValue);
        return configValue;
    }


    public <T, V> MapConfig<T, V> createMap(String key, Map<T, V> defaultValue) {
        return create(new MapConfig<>(key, defaultValue));
    }


    public BooleanConfig createBoolean(String key, boolean defaultValue) {
        return create(new BooleanConfig(key, defaultValue));
    }

    public DoubleConfig createDouble(String key, double defaultValue) {
        return create(new DoubleConfig(key, defaultValue));
    }

    public <E extends Enum<E>> EnumConfig<E> createEnum(String key, E defaultValue, Class<E> eClass) {
        return create(new EnumConfig<>(key, defaultValue, eClass));
    }

    public <E> ListConfig<E> createList(String key, List<E> defaultValue) {
        return create(new ListConfig<>(key, defaultValue));
    }

    public FloatConfig createFloat(String key, float defaultValue) {
        return create(new FloatConfig(key, defaultValue));
    }

    public IntegerConfig createInteger(String key, int defaultValue) {
        return create(new IntegerConfig(key, defaultValue));
    }

    public StringConfig createString(String key, String defaultValue) {
        return create(new StringConfig(key, defaultValue));
    }

    public Provider<?> getProvider() {
        return provider;
    }
}
