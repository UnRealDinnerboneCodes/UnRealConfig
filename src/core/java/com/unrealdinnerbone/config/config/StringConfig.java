package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.NotNull;

public class StringConfig extends ConfigValue<String> {

    public StringConfig(ConfigID id, IProvider provider, String defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    protected @NotNull <B> String from(Class<B> clazz, B value) throws ConfigParseException {
        return value == null ? "null" : value.toString();
    }

    @Override
    public Class<String> getClassType() {
        return String.class;
    }

}
