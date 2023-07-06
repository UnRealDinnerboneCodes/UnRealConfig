package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;

import java.util.HashMap;
import java.util.Map;

public class ArgsProvider implements IProvider {
    private final Map<String, String> args = new HashMap<>();

    public ArgsProvider(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String[] split = arg.split("=");
                if (split.length == 2) {
                    this.args.put(split[0].substring(2), split[1]);
                } else {
                    this.args.put(split[0].substring(2), "true");
                }
            }
        }
    }
    @Override
    public <T> T get(ConfigValue<T> value) throws ConfigParseException, ConfigNotFoundException {
        if (args.containsKey(value.getId().toString("_"))) {
            return value.from(String.class, args.get(value.getId().toString("_")));
        } else {
            throw new ConfigNotFoundException("Could not find config value: " + value.getId().toString("_"));
        }
    }
}
