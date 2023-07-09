package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ListConfig<T> extends ConfigValue<T[]> {


    private final Class<T[]> clazz;

    public ListConfig(Namespace id, IProvider provider, @Nullable T[] defaultValue, Class<T[]> clazz) {
        super(id, provider, defaultValue);
        this.clazz = clazz;

    }

    @Override
    public <B> @NotNull T @NotNull [] from(Class<B> clazz, B value) throws ConfigParseException {
        if(clazz.isArray() && clazz.getComponentType().isAssignableFrom(this.clazz.getComponentType())) {
            return (T[]) value;
        } else {
            throw new ConfigParseException("Could not parse " + clazz.getName() + " to " + this.clazz.getName());
        }
    }

    @Override
    public Class<T[]> getClassType() {
        return clazz;
    }

}
