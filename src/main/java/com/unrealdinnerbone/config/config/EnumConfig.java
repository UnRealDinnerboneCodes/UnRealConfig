package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumConfig<T extends Enum<T>> extends ConfigValue<T> {

    private final Class<T> type;
    private final Map<String, T> idMap = new HashMap<>();
    private final List<String> examples;

    public EnumConfig(Namespace id, IProvider provider, T defaultValue, Class<T> clazz) {
        super(id, provider, defaultValue);
        this.type = clazz;
        Arrays.stream(clazz.getEnumConstants()).forEach(enumConstant -> idMap.put(enumConstant.name().toLowerCase(), enumConstant));
        examples = idMap.keySet().stream().toList();
    }

    @Override
    public @NotNull T fromObject(Object o) {
        if(idMap.containsKey(o.toString().toLowerCase())) {
            return idMap.get(o.toString().toLowerCase());
        }else {
            throw new IllegalArgumentException("Cant find enum with name " + o);
        }
    }

    @Override
    public List<String> getExamples() {
        return examples;
    }

    @Override
    public Class<T> getClassType() {
        return type;
    }
}
