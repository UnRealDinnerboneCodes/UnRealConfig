package com.unrealdinnerbone.test;

import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import com.unrealdinnerbone.config.impl.provider.EnvProvider;
import com.unrealdinnerbone.test.data.TestConfig;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class EnvProviderTest {

    @Test
    public void test() throws ConfigException {
        Map<String, String> map = new HashMap<>();
        map.put("STRING", "Hello");
        map.put("BOOLEAN", "true");
        map.put("DOUBLE", "1.0");
        map.put("FLOAT", "1.0");
        map.put("INTEGER", "1");
        map.put("ENUM", "BAD");
        map.put("LIST", "Cake,Cheese");
        map.put("MAP", "Hello=World,Hello2=World2");
        EnvProvider argsProvider = new EnvProviderTest.TestEnvProvider(map);
        TestConfig testConfig = argsProvider.loadConfig(TestConfig::new);
        ConfigTest.assetConfigDefaultValues(testConfig);
        argsProvider.read();
        ConfigTest.changeConfigValues(testConfig);
    }


    public static class TestEnvProvider extends EnvProvider {

        private final Map<String, String> envMap;

        public TestEnvProvider(Map<String, String> envMap) {
            this.envMap = envMap;
        }


        @Override
        public Map<String, String> getEnvMap() {
            return envMap;
        }
    }
}
