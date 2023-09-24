package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MapConfig<T, V> extends ConfigValue<Map<T, V>> {

    private final Class<Map<T, V>> mapClass;

    public MapConfig(ConfigID id, IProvider provider, Class<Map<T, V>> mapClass, @Nullable Map<T, V> defaultValue) {
        super(id, provider, defaultValue);
        this.mapClass = mapClass;
    }

    @Override
    protected @NotNull <B> Map<T, V> from(Class<B> clazz, B value) throws ConfigParseException {
        throw new ConfigParseException("Could not parse " + value + " to " + getClassType());
    }

    @Override
    public Class<Map<T, V>> getClassType() {
        return mapClass;
    }
}
