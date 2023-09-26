package com.unrealdinnerbone.config.config;


public class DoubleConfig extends NumberConfig<Double> {

    public DoubleConfig(String id, Double defaultValue) {
        super(id, defaultValue);
    }

    @Override
    public Double numberValue(Number number) {
        return number.doubleValue();
    }



}
