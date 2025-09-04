package com.unrealdinnerbone.config.api;

import com.unrealdinnerbone.config.config.*;
import com.unrealdinnerbone.config.config.ConfigCategory;
import com.unrealdinnerbone.config.config.ConfigValue;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ConfigCreator {

    protected final ConfigCategory category;
    protected boolean checkForDuplicates;

    public ConfigCreator(ConfigCategory category) {
        this.category = category;
        this.checkForDuplicates = true;
    }

    protected ConfigCreator(ConfigCreator category) {
        this.category = category.category;
        this.checkForDuplicates = category.checkForDuplicates;
    }

    public <D, R extends ConfigValue<D>> R create(R configValue) {
        category.add(configValue, checkForDuplicates);
        return configValue;
    }

    public <D, R extends ConfigValue<D>> R create(Function<Provider, R> creatorFunction) {
        return create(creatorFunction.apply(category.getProvider()));
    }

    public ConfigCategory createCategory(String name) {
        return create(new ConfigCategory(category.getProvider(), name));
    }

    public <T> T createCategory(String name, Function<ConfigCreator, T> creatorFunction) {
        ConfigCreator group = createCategory(name).getCreator();
        return creatorFunction.apply(group);
    }

    public <T> ConfigValue<T> createGeneric(String key, @Nullable T defaultValue, Class<T> clazz) {
        return create(new TypedConfigValue<>(category.getProvider(), key, defaultValue, clazz));
    }

    public <K, V> MapConfigValue<K, V> createMap(String key, Map<K, V> defaultValue, Class<K> kClass, Class<V> vClass) {
        return create(new MapConfigValue<>(category.getProvider(), key, defaultValue, kClass, vClass));
    }

    public <V> MapConfigValue<String, V> createMap(String key, Map<String, V> defaultValue, Class<V> clazz) {
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

    public <E> ListConfigValue<E> createList(String key, List<E> defaultValue, Class<E> clazz) {
        return create(new ListConfigValue<>(category.getProvider(), key, defaultValue, clazz));
    }

    public <E> ListConfigValue<E> createList(String key, Class<E> clazz) {
        return createList(key, new ArrayList<>(), clazz);
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
