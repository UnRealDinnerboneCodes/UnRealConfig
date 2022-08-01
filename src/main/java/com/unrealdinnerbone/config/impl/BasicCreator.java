package com.unrealdinnerbone.config.impl;

import com.unrealdinnerbone.config.ConfigCreator;
import com.unrealdinnerbone.config.IConfigCreator;
import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.ID;
import com.unrealdinnerbone.config.api.IProvider;

import java.util.function.Consumer;

public record BasicCreator(String id, IProvider provider, Consumer<ConfigValue<?>> creationCallback) implements IConfigCreator {

    @Override
    public <D, R extends ConfigValue<D>> R create(String key, D defaultValue, ConfigCreator<D, R> function) {
        System.out.println(id + key);
        R apply = function.apply(ID.of(id, key), provider, defaultValue);
        creationCallback.accept(apply);
        return apply;
    }
}
