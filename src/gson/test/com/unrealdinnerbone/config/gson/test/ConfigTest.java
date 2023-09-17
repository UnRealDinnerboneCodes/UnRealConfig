package com.unrealdinnerbone.config.gson.test;

import com.unrealdinnerbone.config.ConfigCreator;
import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.api.ClassMapper;
import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.config.IntegerConfig;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
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
    public void testInvalidating() throws ConfigException {
        ConfigManager configManger =new ConfigManager(new IProvider() {

            private static int count = 0;
            @Override
            public <T> @Nullable T get(ConfigID id, Class<T> tClass, ClassMapper<T> mapper) throws ConfigParseException {
                return mapper.map(String.class, String.valueOf(count++));
            }
        });

        SimpleConfig testConfig = configManger.loadConfig("test", SimpleConfig::new);
        Assertions.assertEquals(0, testConfig.getIntegerConfig().getExceptionally());
        testConfig.getIntegerConfig().invalidate();
        Assertions.assertEquals(1, testConfig.getIntegerConfig().getExceptionally());

    }

    public class SimpleConfig {

        private final IntegerConfig integerConfig;
        public SimpleConfig(ConfigCreator creator) {
            this.integerConfig = creator.createInteger("integer", 0);
        }

        public IntegerConfig getIntegerConfig() {
            return integerConfig;
        }
    }

    @Test
    public void testBadBoolean() {
        Assertions.assertThrows(ConfigParseException.class, () -> new ConfigManager(new ArgsProvider(new String[]{"--test_boolean=BAD"}))
                .loadConfig("test", TestConfig::new)
                .booleanConfig.getExceptionally());
    }
}
