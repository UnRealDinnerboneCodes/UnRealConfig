package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest
{
    @Test
    public void testConfig() {
        assertTrue(new ConfigManager(new TestProvider(s -> "true")).loadConfig(TestConfig::new).getBooleanConfig());
    }

    @Test
    public void testBadBoolean() {
        assertThrows(IllegalArgumentException.class, () -> new ConfigManager(new TestProvider(s -> "cake")).loadConfig(TestConfig::new).getBooleanConfig());

    }
}
