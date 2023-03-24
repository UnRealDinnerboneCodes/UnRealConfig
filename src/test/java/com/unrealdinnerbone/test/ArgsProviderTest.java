package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.ConfigManager;
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
        Assert.assertEquals("Hello", testConfig1.stringConfig.getValue());
        Assert.assertEquals(true, testConfig1.booleanConfig.getValue());
        Assert.assertEquals(1.0, testConfig1.doubleConfig.getValue(), 0.0);
        Assert.assertEquals(1.0F, testConfig1.floatConfig.getValue(), 0.0F);
        Assert.assertEquals(1, testConfig1.integerConfig.getValue().intValue());
        Assert.assertEquals(TestEnum.BAD, testConfig1.enumConfig.getValue());
    }
}
