package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.ID;
import org.jetbrains.annotations.NotNull;

public class FloatConfig extends ConfigValue<Float> {

    public FloatConfig(ID id, IProvider provider, Float defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    public @NotNull Float fromObject(Object o) {
        return Float.parseFloat(String.valueOf(o));
    }
}
