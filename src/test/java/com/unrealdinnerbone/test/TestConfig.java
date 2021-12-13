package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.IConfig;

public class TestConfig implements IConfig {

    private String test;

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getFolderName() {
        return "test";
    }

    public String getTest() {
        return test;
    }
}
