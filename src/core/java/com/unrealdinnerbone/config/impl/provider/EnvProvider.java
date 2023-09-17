package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ClassMapper;
import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.Nullable;

public record EnvProvider(String split) implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider("_");

    @Override
    public <T> @Nullable T get(ConfigID id, Class<T> tClass, ClassMapper<T> mapper) throws ConfigParseException, ConfigNotFoundException {
        String configId = id.key() + split + id.value();
        if(!System.getenv().containsKey(configId)) {
            throw new ConfigNotFoundException("Could not find envfigriable " + configId);
        }else {
            return mapper.map(String.class, System.getenv(configId));
        }
    }
}
