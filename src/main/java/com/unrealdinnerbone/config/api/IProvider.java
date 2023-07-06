package com.unrealdinnerbone.config.api;

import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.Nullable;

public interface IProvider {

    /**
     * @param value the config value to retrieve the config
     * @param <T> the config value
     * @return null if the stored config is null not IF THE config is missing
     * @throws ConfigParseException if unable to parse the config
     * @throws ConfigNotFoundException if the config is not found
     */
    @Nullable
    <T> T get(ConfigValue<T> value) throws ConfigParseException, ConfigNotFoundException;

    default <T> boolean save(Namespace id, Class<T> tClass, T value)  {
        return false;
    }

    default <T> void setDefault(Namespace id, Class<T> tClass, T value) {};

}
