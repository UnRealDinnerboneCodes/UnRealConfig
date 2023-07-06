package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import org.junit.Assert;
import org.junit.Test;

public class ArgsProviderTest {

    @Test
    public void test() {
        String[] args = new String[]{"--test_string=Hello", "--test_boolean", "--test_double=1.0", "--test_float=1.0", "--test_integer=1", "--test_enum=BAD"};
        ArgsProvider argsProvider = new ArgsProvider(args);
        ConfigManager configManager = new ConfigManager(argsProvider);
        TestConfig testConfig1 = configManager.loadConfig("test", TestConfig::new);
        try {
            Assert.assertEquals("Hello", testConfig1.stringConfig.getExceptionally());
            Assert.assertEquals(true, testConfig1.booleanConfig.getExceptionally());
            Assert.assertEquals(1.0, testConfig1.doubleConfig.getExceptionally(), 0.0);
            Assert.assertEquals(1.0F, testConfig1.floatConfig.getExceptionally(), 0.0F);
            Assert.assertEquals(1, testConfig1.integerConfig.getExceptionally().intValue());
            Assert.assertEquals(TestEnum.BAD, testConfig1.enumConfig.getExceptionally());
        }catch (ConfigException e) {
            Assert.fail(e.getMessage());
        }
    }
}
