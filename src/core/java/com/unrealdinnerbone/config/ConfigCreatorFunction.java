package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;

public interface ConfigCreatorFunction<D, R extends ConfigValue<D>> {
    R apply(ConfigID id, IProvider provider, D defaultValue);
}
