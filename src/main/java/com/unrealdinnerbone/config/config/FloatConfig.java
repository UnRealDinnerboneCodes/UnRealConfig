package com.unrealdinnerbone.config.config;

public class FloatConfig extends NumberConfig<Float> {

    public FloatConfig(String id, Float defaultValue)  {
        super(id, defaultValue);
    }
    @Override
    public Float numberValue(Number number) {
        return number.floatValue();
    }



}
