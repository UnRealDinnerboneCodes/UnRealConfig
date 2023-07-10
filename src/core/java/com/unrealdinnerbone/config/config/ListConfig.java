package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ListConfig<T> extends ConfigValue<T[]> {


    private final Class<T[]> clazz;

    @Nullable
    private Supplier<List<String>> examples;

    public ListConfig(Namespace id, IProvider provider, @Nullable T[] defaultValue, Class<T[]> clazz) {
        super(id, provider, defaultValue);
        this.clazz = clazz;
    }

    public void setExamples(Supplier<List<String>> examples) {
        this.examples = examples;
    }

    public void addValue(T value) throws ConfigParseException {
        Optional<T[]> optionalTS = get();
        if(optionalTS.isPresent()) {
            T[] a = optionalTS.get();
            List<T> list = new ArrayList<>(Arrays.asList(a));
            list.add(value);
            T[] newArray = list.toArray(a);
            set(newArray);
        }
    }

    public void removeValue(T value) throws ConfigParseException {
        Optional<T[]> optionalTS = get();
        if(optionalTS.isPresent()) {
            T[] a = optionalTS.get();
            List<T> list = new ArrayList<>(Arrays.asList(a));
            list.remove(value);
            T[] newArray = list.toArray(a);
            set(newArray);
        }
    }

    @Override
    public <B> @NotNull T @NotNull [] from(Class<B> clazz, B value) throws ConfigParseException {
        if(clazz.isArray() && clazz.getComponentType().isAssignableFrom(this.clazz.getComponentType())) {
            return (T[]) value;
        } else {
            throw new ConfigParseException("Could not parse " + clazz.getName() + " to " + this.clazz.getName());
        }
    }

    @Override
    public Class<T[]> getClassType() {
        return clazz;
    }

}
