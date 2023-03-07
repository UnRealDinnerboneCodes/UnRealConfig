package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest
{
    @Test
    public void testConfig() {
        ConfigManager configManager = new ConfigManager(new TestProvider(s -> "true"));
        TestConfig testConfig = configManager.loadConfig("test", TestConfig::new);
        assertTrue(testConfig.getBooleanConfig());
    }

    @Test
    public void testBadBoolean() {
        assertThrows(IllegalArgumentException.class, () -> new ConfigManager(new TestProvider(s -> "cake"))
                .loadConfig("test", TestConfig::new)
                .getBooleanConfig());

    }
}
