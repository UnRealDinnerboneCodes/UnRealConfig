package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;

public abstract class NumberConfig<T extends Number> extends ConfigValue<T> {


    public NumberConfig(Namespace id, IProvider provider, T defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    protected @NotNull <B> T from(Class<B> clazz, B value) throws ConfigParseException {
        if(value instanceof Number number) {
            return numberValue(number);
        }
        if(value instanceof String s) {
            try {
                return fromString(s);
            } catch (NumberFormatException e) {
                throw new ConfigParseException("Cannot parse string " + s + " to a " + getName());
            }
        }
        throw new ConfigParseException("Cannot parse " + clazz.getName() + " to a " + getName());
    }

    public abstract T numberValue(Number number);

    public abstract T fromString(String string) throws NumberFormatException;

    public abstract String getName();


}
