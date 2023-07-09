package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigTest {
    @Test
    public void testConfig() {
        ConfigManager configManager = new ConfigManager(new ArgsProvider(new String[]{"--test_boolean=true"}));
        TestConfig testConfig = configManager.loadConfig("test", TestConfig::new);
        try {
            Assertions.assertTrue(testConfig.booleanConfig.getExceptionally());
        } catch (ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testBadBoolean() {
        Assertions.assertThrows(ConfigParseException.class, () -> new ConfigManager(new ArgsProvider(new String[]{"--test_boolean=BAD"}))
                .loadConfig("test", TestConfig::new)
                .booleanConfig.getExceptionally());
    }
}
