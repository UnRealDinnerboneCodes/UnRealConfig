package com.unrealdinnerbone.test;


import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.impl.provider.ArgsProvider;
import com.unrealdinnerbone.test.data.TestConfig;
import com.unrealdinnerbone.test.data.TestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ConfigTest {
    @Test
    public void testConfig() {
        ArgsProvider<TestConfig> argsProvider = new ArgsProvider<>(new String[]{"--test_boolean=true"}, TestConfig::new);
        TestConfig testConfig = argsProvider.getConfig();
        Assertions.assertTrue(testConfig.booleanConfig.get());
    }


    public static void assetConfigDefaultValues(TestConfig testConfig, boolean complex) {
        Assertions.assertNull(testConfig.stringConfig.get());
        Assertions.assertEquals(true, testConfig.booleanConfig.get());
        Assertions.assertEquals(0.0, testConfig.doubleConfig.get());
        Assertions.assertEquals(0.0F, testConfig.floatConfig.get());
        Assertions.assertEquals(0, testConfig.integerConfig.get());
        Assertions.assertEquals(TestEnum.GOOD, testConfig.enumConfig.get());
        Assertions.assertEquals("Hello World", testConfig.nestedConfigOne.get());
        if(complex) {
            Assertions.assertTrue(testConfig.listConfig.get().containsAll(List.of("Test2", "Test2")));
            Assertions.assertEquals(Map.of("Hello", "World", "Hello2", "World2"), testConfig.mapConfig.get());
        }
    }

    public static void changeConfigValues(TestConfig testConfig) {
        testConfig.stringConfig.setValue("Hello World");
        testConfig.booleanConfig.setValue(false);
        testConfig.doubleConfig.setValue(1.0);
        testConfig.floatConfig.setValue(1.0F);
        testConfig.integerConfig.setValue(1);
        testConfig.enumConfig.setValue(TestEnum.BAD);
        testConfig.listConfig.setValue(List.of("Hello World", "Hello World Again"));
        testConfig.mapConfig.setValue(Map.of("Hello", "General", "Hello2", "Kenobi"));
        testConfig.nestedConfigOne.setValue("Hello?");
    }

    public static void assertChangedConfigValues(TestConfig testConfig, boolean complex) {
        String actual = testConfig.stringConfig.get();
        Assertions.assertEquals("Hello World", actual);
        Assertions.assertEquals(false, testConfig.booleanConfig.get());
        Assertions.assertEquals(1.0, testConfig.doubleConfig.get());
        Assertions.assertEquals(1.0F, testConfig.floatConfig.get());
        Assertions.assertEquals(1, testConfig.integerConfig.get());
        Assertions.assertEquals(TestEnum.BAD, testConfig.enumConfig.get());
        Assertions.assertEquals("Hello?", testConfig.nestedConfigOne.get());
        if(complex) {
            Assertions.assertTrue(testConfig.listConfig.get().containsAll(List.of("Hello World", "Hello World Again")));
            Map<String, String> exceptionally = testConfig.mapConfig.get();
            Assertions.assertEquals("General", exceptionally.get("Hello"));
            Assertions.assertEquals("Kenobi", exceptionally.get("Hello2"));
            Map<String, TestConfig.TestData> stringTestEnumMap = testConfig.enumMapConfig.get();
            for (Map.Entry<String, TestConfig.TestData> value : stringTestEnumMap.entrySet()) {
                Assertions.assertEquals(value.getKey(), value.getValue().value());
            }
        }
    }

    @Test
    public void testBadEnumValue() throws IOException, ConfigException {
        ArgsProvider<TestConfig> provider = new ArgsProvider<>(new String[]{"--enum=TRASH"}, TestConfig::new);
        TestConfig testConfig = provider.getConfig();
        provider.read();
        Assertions.assertNull(testConfig.enumConfig.get());
    }
}
