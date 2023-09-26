package com.unrealdinnerbone.test.data;

import com.unrealdinnerbone.config.api.ConfigCreator;
import com.unrealdinnerbone.config.config.*;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;
import java.util.Map;

public class TestConfig {

    public final ConfigValue<Boolean> booleanConfig;

    public final ConfigValue<Double> doubleConfig;

    public final ConfigValue<TestEnum> enumConfig;

    public final ConfigValue<Float> floatConfig;

    public final ConfigValue<Integer> integerConfig;
    public final ConfigValue<String> stringConfig;

    public final ConfigValue<List<String>> listConfig;

    public final ConfigValue<Map<String, String>> mapConfig;

    public final ConfigValue<Map<String, TestData>> enumMapConfig;

    public final ConfigValue<String> nestedConfigOne;
    public TestConfig(ConfigCreator creator) {
        this.stringConfig = creator.createString("string", null);
        this.booleanConfig = creator.createBoolean("boolean", true);
        this.doubleConfig = creator.createDouble("double", 0.0);
        this.floatConfig = creator.createFloat("float", 0.0F);
        this.integerConfig = creator.createInteger("integer", 0);
        this.enumConfig = creator.createEnum("enum", TestEnum.GOOD, TestEnum.class);
        this.listConfig = creator.createList("list", List.of("Test1", "Test2"), String.class);
        this.mapConfig = creator.createMap("map", Map.of("Hello", "World", "Hello2","World2"), String.class);
        this.enumMapConfig = creator.createMap("enum_map", Map.of("Good", new TestData("Good"), "Bad", new TestData("Bad")), TestData.class);

        ConfigCreator nestedCreator = creator.createGroup("nested");
        nestedConfigOne = nestedCreator.createString("string", "Hello World");

    }


    public record TestData(String value) {}

}
