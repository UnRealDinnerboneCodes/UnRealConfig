package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ClassMapper;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.Nullable;

public record EnvProvider(String split) implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider("_");

    @Override
    public <T> @Nullable T get(Namespace id, Class<T> tClass, ClassMapper<T> mapper) throws ConfigParseException, ConfigNotFoundException {
        String configId = id.toString(split);
        if(!System.getenv().containsKey(configId)) {
            throw new ConfigNotFoundException("Could not find envfigriable " + configId);
        }else {
            return mapper.map(String.class, System.getenv(configId));
        }
    }
}
