package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.api.IConfig;
import org.junit.Assert;
import org.junit.Test;

public class ConfigTest
{

    @Test
    public void testConfig() {
        ConfigManager configManager = new ConfigManager()
                .doSaving(false)
                .addProvider(new TestProvider(s -> "cake"));

        TestConfig testConfig = configManager.loadConfig(new TestConfig());

        Assert.assertEquals(testConfig.getTest(), "cake");
    }
}
