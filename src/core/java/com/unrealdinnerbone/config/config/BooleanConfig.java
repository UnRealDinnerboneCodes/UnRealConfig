package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigParseException;
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
    public @NotNull <B> Boolean from(Class<B> clazz, B value) throws ConfigParseException {
        if (value instanceof String s) {
            if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(s);
            } else {
                throw new ConfigParseException("Cannot parse string " + s + " to a boolean");
            }
        } else {
            throw new ConfigParseException("Cannot parse " + clazz.getName() + " to a boolean");
        }
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
