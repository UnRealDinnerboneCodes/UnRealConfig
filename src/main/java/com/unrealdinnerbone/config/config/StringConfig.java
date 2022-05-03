package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.ID;
import org.jetbrains.annotations.NotNull;

public class StringConfig extends ConfigValue<String> {

    public StringConfig(ID id, IProvider provider, String defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    public Class<String> getClassType() {
        return String.class;
    }

    @Override
    public @NotNull String fromObject(Object o) {
        return o.toString();
    }

}
