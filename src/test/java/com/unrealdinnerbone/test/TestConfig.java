package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.IConfigCreator;
import com.unrealdinnerbone.config.config.BooleanConfig;

public class TestConfig {

    private final BooleanConfig booleanConfig;

    public TestConfig(IConfigCreator creator) {
        this.booleanConfig = creator.create("booleanConfig", true, BooleanConfig::new);
    }

    public boolean getBooleanConfig() {
        return booleanConfig.getValue();
    }
}
