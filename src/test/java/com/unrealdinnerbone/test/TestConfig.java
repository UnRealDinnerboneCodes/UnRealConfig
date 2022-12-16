package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.IConfigCreator;
import com.unrealdinnerbone.config.config.BooleanConfig;
import com.unrealdinnerbone.config.config.StringConfig;

public class TestConfig {

    private final BooleanConfig booleanConfig;
    private final StringConfig stringConfig;

    public TestConfig(IConfigCreator creator) {
        this.stringConfig = creator.createString("string", null);
        this.booleanConfig = creator.createBoolean("booleanConfig", true);
    }

    public boolean getBooleanConfig() {
        return booleanConfig.getValue();
    }
}
