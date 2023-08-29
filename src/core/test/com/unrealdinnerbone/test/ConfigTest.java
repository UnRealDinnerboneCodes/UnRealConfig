package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.api.ClassMapper;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import com.unrealdinnerbone.unreallib.Namespace;
import org.jetbrains.annotations.Nullable;
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
    public void testList() {
        ConfigManager configManager = new ConfigManager(new IProvider() {
            @Override
            public <T> @Nullable T get(Namespace id, Class<T> tClass, ClassMapper<T> mapper) throws ConfigParseException, ConfigNotFoundException {
                return mapper.map(String[].class, new String[]{"one", "two"});
            }

        });
        TestConfig testConfig = configManager.loadConfig("test", TestConfig::new);
        try {
            Assertions.assertEquals(2, testConfig.stringListConfig.getExceptionally().length);
            testConfig.stringListConfig.addValue("three");
            Assertions.assertEquals(3, testConfig.stringListConfig.getExceptionally().length);
            testConfig.stringListConfig.removeValue("three");
            Assertions.assertEquals(2, testConfig.stringListConfig.getExceptionally().length);
        } catch (ConfigException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testBadBoolean() {
        Assertions.assertThrows(ConfigParseException.class, () -> new ConfigManager(new ArgsProvider(new String[]{"--test_boolean=BAD"}))
                .loadConfig("test", TestConfig::new)
                .booleanConfig.getExceptionally());
    }
}
