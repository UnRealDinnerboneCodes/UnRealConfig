package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.ClassMapper;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.Nullable;

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
    public <T> @Nullable T get(Namespace id, Class<T> tClass, ClassMapper<T> mapper) throws ConfigParseException, ConfigNotFoundException {
        String configId = id.toString("_");
        if (args.containsKey(configId)) {
            return mapper.map(String.class, args.get(configId));
        } else {
            throw new ConfigNotFoundException("Could not find config value: " + configId);
        }
    }
}
