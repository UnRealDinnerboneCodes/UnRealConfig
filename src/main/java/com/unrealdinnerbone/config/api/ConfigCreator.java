package com.unrealdinnerbone.config.api;

import com.google.gson.reflect.TypeToken;
import com.unrealdinnerbone.config.config.*;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConfigCreator {


    private final ConfigCategory category;

    private boolean checkForDuplicates;
    public ConfigCreator(ConfigCategory category) {
        this.category = category;
        this.checkForDuplicates = true;
    }

    public <D, R extends ConfigValue<D>> R create(R configValue) {
        category.add(configValue, checkForDuplicates);
        return configValue;
    }

    public ConfigCategory createCategory(String name) {
        return create(new ConfigCategory(name));
    }


    public <T> T createCategory(String name, Function<ConfigCreator, T> creatorFunction) {
        ConfigCreator group = createCategory(name).getCreator();
        return creatorFunction.apply(group);
    }


    public <T> ConfigValue<T> createGeneric(String key, T defaultValue, Class<T> clazz) {
        return create(new TypedConfigValue<>(key, defaultValue, clazz));
    }

    public <K, V> ConfigValue<Map<K, V>> createMap(String key, Map<K, V> defaultValue, Class<K> kClass, Class<V> vClass) {
        return create(new TypedConfigValue<>(key, defaultValue, TypeToken.getParameterized(Map.class, kClass, vClass).getType()));
    }

    public <V> ConfigValue<Map<String, V>> createMap(String key, Map<String, V> defaultValue, Class<V> clazz) {
        return createMap(key, defaultValue, String.class, clazz);
    }

    public ConfigValue<Boolean> createBoolean(String key, boolean defaultValue) {
        return createGeneric(key, defaultValue, Boolean.class);
    }

    public ConfigValue<Double> createDouble(String key, double defaultValue) {
        return createGeneric(key, defaultValue, Double.class);
    }

    public <E extends Enum<E>> ConfigValue<E> createEnum(String key, E defaultValue, Class<E> eClass) {
        return createGeneric(key, defaultValue, eClass);
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

    public void setCheckForDuplicates(boolean checkForDuplicates) {
        this.checkForDuplicates = checkForDuplicates;
    }

}
