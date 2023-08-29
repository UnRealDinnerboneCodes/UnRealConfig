package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigParseException;
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
    protected <B> @NotNull T from(Class<B> clazz, B value) throws ConfigParseException {
        if(clazz.equals(type)) {
            return Enum.valueOf(type, value.toString());
        }else if(value instanceof String s) {
            if(idMap.containsKey(s.toLowerCase())) {
                return idMap.get(s.toLowerCase());
            }else {
                throw new ConfigParseException("Cannot parse string " + s + " to enum " + type.getSimpleName());
            }
        }else {
            throw new ConfigParseException("Cannot parse " + clazz.getName() + " to enum " + type.getSimpleName());
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
