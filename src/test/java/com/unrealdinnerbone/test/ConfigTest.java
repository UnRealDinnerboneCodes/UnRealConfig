package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ConfigTest {
    @Test
    public void testConfig() {
        ConfigManager configManager = new ConfigManager(new ArgsProvider(new String[]{"--test_boolean=true"}));
        TestConfig testConfig = configManager.loadConfig("test", TestConfig::new);
        try {
            assertTrue(testConfig.booleanConfig.getExceptionally());
        } catch (ConfigException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testBadBoolean() {
        assertThrows(ConfigParseException.class, () -> new ConfigManager(new ArgsProvider(new String[]{"--test_boolean=BAD"}))
                .loadConfig("test", TestConfig::new)
                .booleanConfig.getExceptionally());
    }
}
