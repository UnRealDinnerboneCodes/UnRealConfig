package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.Nullable;

public class DefaultConfigProvider implements IProvider {

    @Override
    public <T> @Nullable T get(ConfigValue<T> value) throws ConfigParseException, ConfigNotFoundException {
        return value.getDefaultValue();
    }
}
