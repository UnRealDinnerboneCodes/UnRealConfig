package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.unreallib.Namespace;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Object> get(Namespace id) {
        if(args.containsKey(id.toString("_"))) {
            return Optional.of(args.get(id.toString("_")));
        }else {
            return Optional.empty();
        }
    }


}
