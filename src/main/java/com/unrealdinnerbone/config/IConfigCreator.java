package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.config.*;

public interface IConfigCreator {
    <D, R extends ConfigValue<D>> R create(String key, D defaultValue, ConfigCreator<D, R> function);

    default BooleanConfig createBoolean(String key, boolean defaultValue) {
        return create(key, defaultValue, BooleanConfig::new);
    }

    default DoubleConfig createDouble(String key, double defaultValue) {
        return create(key, defaultValue, DoubleConfig::new);
    }

    default <E extends Enum<E>> EnumConfig<E> createEnum(String key, E defaultValue, Class<E> eClass) {
        return create(key, defaultValue, (namespace, provider, defaultValue1) -> new EnumConfig<>(namespace, provider, defaultValue1, eClass));
    }

    default FloatConfig createFloat(String key, float defaultValue) {
        return create(key, defaultValue, FloatConfig::new);
    }

    default IntegerConfig createInteger(String key, int defaultValue) {
        return create(key, defaultValue, IntegerConfig::new);
    }

    default StringConfig createString(String key, String defaultValue) {
        return create(key, defaultValue, StringConfig::new);
    }
}
