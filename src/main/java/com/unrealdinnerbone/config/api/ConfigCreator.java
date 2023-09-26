package com.unrealdinnerbone.config.api;

import com.google.gson.reflect.TypeToken;
import com.sun.source.util.Plugin;
import com.unrealdinnerbone.config.config.*;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;

import java.lang.reflect.Type;
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

    public <T> ConfigValue<T> createGeneric(String key, T defaultValue, Class<T> clazz) {
        return create(new TypedConfigValue<T>(key, defaultValue, clazz));
    }


    public <V> ConfigValue<Map<String, V>> createMap(String key, Map<String, V> defaultValue, Class<V> clazz) {
        return create(new TypedConfigValue<>(key, defaultValue, TypeToken.getParameterized(Map.class, String.class, clazz).getType()));
    }

    public ConfigValue<Boolean> createBoolean(String key, boolean defaultValue) {
        return createGeneric(key, defaultValue, Boolean.class);
    }

    public ConfigValue<Double> createDouble(String key, double defaultValue) {
        return createGeneric(key, defaultValue, Double.class);
    }

    public <E extends Enum<E>> ConfigValue<E> createEnum(String key, E defaultValue, Class<E> eClass) {
        return create(createGeneric(key, defaultValue, eClass));
    }

    public <E> ConfigValue<List<E>> createList(String key, List<E> defaultValue, Class<E> clazz) {
        return create(new TypedConfigValue<>(key, defaultValue, TypeToken.getParameterized(List.class, clazz).getType()));
    }
    public ConfigValue<Float> createFloat(String key, float defaultValue) {
        return createGeneric(key, defaultValue, Float.class);
    }

    public ConfigValue<Integer> createInteger(String key, int defaultValue) {
        return createGeneric(key, defaultValue, Integer.class);
    }

    public ConfigValue<String> createString(String key, String defaultValue) {
        return createGeneric(key, defaultValue, String.class);
    }

    public Provider<?> getProvider() {
        return provider;
    }
}
