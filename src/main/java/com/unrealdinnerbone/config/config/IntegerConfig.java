package com.unrealdinnerbone.config.config;

public class IntegerConfig extends NumberConfig<Integer> {

    public IntegerConfig(String id, Integer defaultValue)  {
        super(id, defaultValue);
    }

    @Override
    public Integer numberValue(Number number) {
        return number.intValue();
    }



}
