package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.config.*;
import com.unrealdinnerbone.unreallib.Namespace;

import java.util.function.Consumer;

public record ConfigCreator(String id, IProvider provider, Consumer<ConfigValue<?>> creationCallback) {

    public <D, R extends ConfigValue<D>> R create(String key, D defaultValue, ConfigCreatorFunction<D, R> function) {
        R apply = function.apply(Namespace.of(id, key), provider, defaultValue);
        creationCallback.accept(apply);
        provider.onConfigCreated(apply);
        return apply;
    }

    public BooleanConfig createBoolean(String key, boolean defaultValue) {
        return create(key, defaultValue, BooleanConfig::new);
    }

    public DoubleConfig createDouble(String key, double defaultValue) {
        return create(key, defaultValue, DoubleConfig::new);
    }

    public <E extends Enum<E>> EnumConfig<E> createEnum(String key, E defaultValue, Class<E> eClass) {
        return create(key, defaultValue, (Namespace namespace, IProvider provider, E defaultValue1) -> {
            return new EnumConfig<>(namespace, provider, defaultValue1, eClass);
        });
    }

    public <E> ListConfig<E> createList(String key, E[] defaultValue, Class<E[]> clazz) {
        return create(key, defaultValue, (Namespace namespace, IProvider provider, E[] defaultValue1) -> {
            return new ListConfig<>(namespace, provider, defaultValue1, clazz);
        });
    }
    public FloatConfig createFloat(String key, float defaultValue) {
        return create(key, defaultValue, FloatConfig::new);
    }

    public IntegerConfig createInteger(String key, int defaultValue) {
        return create(key, defaultValue, IntegerConfig::new);
    }

    public StringConfig createString(String key, String defaultValue) {
        return create(key, defaultValue, StringConfig::new);
    }
}
