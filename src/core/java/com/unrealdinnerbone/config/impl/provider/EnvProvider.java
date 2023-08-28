package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record EnvProvider(String split) implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider("_");

    @Override
    @NotNull
    public <T> T get(ConfigValue<T> configValue) throws ConfigParseException, ConfigNotFoundException {
        String envId = configValue.getId().toString(split);
        if(!System.getenv().containsKey(envId)) {
            throw new ConfigNotFoundException("Could not find env variable " + envId);
        }else {
            return configValue.from(String.class, System.getenv().get(envId));
        }
    }
}
