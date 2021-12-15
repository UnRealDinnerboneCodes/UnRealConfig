package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.api.Namespace;
import com.unrealdinnerbone.config.config.BooleanConfig;

public class TestConfig {

    private final BooleanConfig booleanConfig;

    public TestConfig(IProvider provider) {
        this.booleanConfig = new BooleanConfig(Namespace.of("test", "boolean_test"), provider, true);
    }

    public boolean getBooleanConfig() {
        return booleanConfig.getValue();
    }
}
