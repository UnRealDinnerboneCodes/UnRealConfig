package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;

import java.util.function.Consumer;

public interface ICreator {

    IConfigCreator create(String name, IProvider provider, Consumer<ConfigValue<?>> passback);
}
