package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ClassMapper;
import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MemoryConfigProvider implements IProvider {

    private final Map<ConfigID, Object> configValueMap = new HashMap<>();

    @Override
    public <T> @Nullable T get(ConfigID id, Class<T> tClass, ClassMapper<T> clazzMapper) throws ConfigParseException, ConfigNotFoundException {
        if(configValueMap.containsKey(id)) {
            try {
                return ((T) configValueMap.get(id));
            }catch (ClassCastException e) {
                throw new ConfigParseException("Config value " + id + " is not of type " + tClass.getName());
            }
        }else {
            return null;
        }
    }

    @Override
    public <T> boolean save(ConfigID id, Class<T> tClass, T value) {
        configValueMap.put(id, value);
        return true;

    }

    @Override
    public <T> void onConfigCreated(ConfigValue<T> configValue) {
        configValueMap.put(configValue.getId(), configValue.getDefaultValue());
    }
}
