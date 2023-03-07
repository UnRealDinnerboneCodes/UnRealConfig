package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class BooleanConfig extends ConfigValue<Boolean> {

    private final List<String> TYPES = Arrays.asList("true", "false");

    public BooleanConfig(Namespace id, IProvider provider, Boolean defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    public @NotNull Boolean fromObject(Object o) {
        if (o instanceof Boolean booleanValue) {
            return booleanValue;
        }else if (o instanceof String s && (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))) {
            return Boolean.parseBoolean(s);
        }
        throw new IllegalArgumentException(o + " is not a valid boolean value");
    }

    @Override
    public List<String> getExamples() {
        return TYPES;
    }

    @Override
    public Class<Boolean> getClassType() {
        return Boolean.TYPE;
    }

}
