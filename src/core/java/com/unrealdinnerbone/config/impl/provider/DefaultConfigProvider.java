package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfigProvider implements IProvider {

    private final Map<Namespace, Object> configValueMap = new HashMap<>();

    @Override
    public <T> @Nullable T get(ConfigValue<T> value) throws ConfigParseException, ConfigNotFoundException {
        if(configValueMap.containsKey(value.getId())) {
            try {
                return (T) configValueMap.get(value.getId());
            }catch (ClassCastException e) {
                throw new ConfigParseException("Config value " + value.getId() + " is not of type " + value.getClassType().getName());
            }
        }else {
            return value.getDefaultValue();
        }
    }

    @Override
    public <T> boolean save(Namespace id, Class<T> tClass, T value) throws ConfigParseException {
        configValueMap.put(id, value);
        return true;

    }
}
