package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.config.BooleanConfig;
import com.unrealdinnerbone.config.config.StringConfig;
import com.unrealdinnerbone.config.ConfigCreator;

public class TestConfig {

    private final BooleanConfig booleanConfig;
    private final StringConfig stringConfig;

    public TestConfig(ConfigCreator creator) {
        this.stringConfig = creator.createString("string", null);
        this.booleanConfig = creator.createBoolean("booleanConfig", true);
    }

    public boolean getBooleanConfig() {
        return booleanConfig.getValue();
    }
}
