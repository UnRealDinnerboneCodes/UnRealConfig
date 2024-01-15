package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import com.unrealdinnerbone.test.data.TestConfig;
import org.junit.jupiter.api.Test;

public class ArgsProviderTest {

    @Test
    public void test() throws ConfigException {
        String[] args = new String[]{"--string=Hello", "--boolean", "--double=1.0", "--float=1.0", "--integer=1", "--enum=BAD", "--list=Cake,Cheese", "--map=Hello:World,Hello2:World2"};
        ArgsProvider argsProvider = new ArgsProvider(args);
        TestConfig testConfig = argsProvider.loadConfig(TestConfig::new);
        ConfigTest.assetConfigDefaultValues(testConfig);
        argsProvider.read();
        ConfigTest.changeConfigValues(testConfig);
    }
}
