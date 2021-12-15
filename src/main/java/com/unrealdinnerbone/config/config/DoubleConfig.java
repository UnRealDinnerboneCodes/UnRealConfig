package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.Namespace;
import org.jetbrains.annotations.NotNull;

public class DoubleConfig extends ConfigValue<Double> {


    public DoubleConfig(Namespace id, IProvider provider, Double defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    public @NotNull Double fromObject(Object o) throws NumberFormatException {
        return Double.parseDouble(String.valueOf(o));
    }

}
