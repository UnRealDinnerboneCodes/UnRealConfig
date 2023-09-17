package com.unrealdinnerbone.config.config;

import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.IProvider;

public class IntegerConfig extends NumberConfig<Integer> {

    public IntegerConfig(ConfigID id, IProvider provider, Integer defaultValue)  {
        super(id, provider, defaultValue);
    }

    @Override
    public Integer numberValue(Number number) {
        return number.intValue();
    }

    @Override
    public Integer fromString(String string) throws NumberFormatException {
        return Integer.parseInt(string);
    }

    @Override
    public String getName() {
        return "integer";
    }

    @Override
    public Class<Integer> getClassType() {
        return Integer.TYPE;
    }


}
