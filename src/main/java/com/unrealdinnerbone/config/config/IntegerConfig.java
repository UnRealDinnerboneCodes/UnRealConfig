package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;

public class IntegerConfig extends ConfigValue<Integer> {

    public IntegerConfig(Namespace id, IProvider provider, Integer defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    public Class<Integer> getClassType() {
        return Integer.TYPE;
    }

    @Override
    public @NotNull Integer fromObject(Object o) {
        return Integer.parseInt(String.valueOf(o));
    }
}
