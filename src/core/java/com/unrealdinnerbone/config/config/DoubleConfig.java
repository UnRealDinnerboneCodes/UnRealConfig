package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.IProvider;

public class DoubleConfig extends NumberConfig<Double> {

    public DoubleConfig(ConfigID id, IProvider provider, Double defaultValue) {
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
