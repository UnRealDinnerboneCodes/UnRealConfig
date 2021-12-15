package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.Namespace;

public interface ConfigCreator<D, R extends ConfigValue<D>> {
    R apply(Namespace namespace, IProvider provider, D defaultValue);
}