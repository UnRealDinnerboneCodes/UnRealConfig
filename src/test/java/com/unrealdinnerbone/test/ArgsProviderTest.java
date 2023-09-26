package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import com.unrealdinnerbone.test.data.TestConfig;
import org.junit.jupiter.api.Test;

public class ArgsProviderTest {

    @Test
    public void test() throws ConfigException {
        String[] args = new String[]{"--string=Hello", "--boolean", "--double=1.0", "--float=1.0", "--integer=1", "--enum=BAD"};
        ArgsProvider<TestConfig> argsProvider = new ArgsProvider<>(args, TestConfig::new);
        ConfigTest.assetConfigDefaultValues(argsProvider.getConfig(), false);
        argsProvider.read();
        ConfigTest.changeConfigValues(argsProvider.getConfig());
    }
}
