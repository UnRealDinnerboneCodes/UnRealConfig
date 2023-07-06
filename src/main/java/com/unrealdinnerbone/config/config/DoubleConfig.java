package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.unreallib.Namespace;

public class DoubleConfig extends NumberConfig<Double> {


    public DoubleConfig(Namespace id, IProvider provider, Double defaultValue) {
        super(id, provider, defaultValue);
    }

    @Override
    public Double numberValue(Number number) {
        return number.doubleValue();
    }

    @Override
    public Double fromString(String string) throws NumberFormatException {
        return Double.parseDouble(string);
    }

    @Override
    public String getName() {
        return "double";
    }

    @Override
    public Class<Double> getClassType() {
        return Double.TYPE;
    }

}
