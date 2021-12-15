package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.ID;

public interface ConfigCreator<D, R extends ConfigValue<D>> {
    R apply(ID namespace, IProvider provider, D defaultValue);
}
