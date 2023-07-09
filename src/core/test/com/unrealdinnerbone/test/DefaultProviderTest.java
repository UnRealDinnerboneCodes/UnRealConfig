package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.impl.provider.DefaultConfigProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultProviderTest {

    @Test
    public void test() {
        ConfigManager configManager = new ConfigManager(new DefaultConfigProvider());
        TestConfig test = configManager.loadConfig("test", TestConfig::new);
        try {
            Assertions.assertNull(test.stringConfig.getExceptionally());
            Assertions.assertTrue(test.booleanConfig.getExceptionally());
            Assertions.assertEquals(0.0, test.doubleConfig.getExceptionally(), 0.0);
            Assertions.assertEquals(0.0F, test.floatConfig.getExceptionally(), 0.0F);
            Assertions.assertEquals(0, test.integerConfig.getExceptionally().intValue());
            Assertions.assertEquals(TestEnum.GOOD, test.enumConfig.getExceptionally());
        }catch (ConfigException e) {
            Assertions.fail(e.getMessage());
        }

        try {
            test.enumConfig.set(TestEnum.BAD);
            Assertions.assertEquals(TestEnum.BAD, test.enumConfig.getExceptionally());
        } catch (ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
