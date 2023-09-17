package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.IProvider;

public class FloatConfig extends NumberConfig<Float> {

    public FloatConfig(ConfigID id, IProvider provider, Float defaultValue)  {
        super(id, provider, defaultValue);
    }
    @Override
    public Float numberValue(Number number) {
        return number.floatValue();
    }

    @Override
    public Float fromString(String string) throws NumberFormatException {
        return Float.parseFloat(string);
    }

    @Override
    public String getName() {
        return "float";
    }

    @Override
    public Class<Float> getClassType() {
        return Float.TYPE;
    }

}
