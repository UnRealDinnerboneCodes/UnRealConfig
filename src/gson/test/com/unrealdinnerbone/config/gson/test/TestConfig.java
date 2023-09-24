package com.unrealdinnerbone.config.gson.test;

import com.unrealdinnerbone.config.ConfigCreator;
import com.unrealdinnerbone.config.config.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConfig {

    public final BooleanConfig booleanConfig;

    public final DoubleConfig doubleConfig;

    public final EnumConfig<TestEnum> enumConfig;

    public final FloatConfig floatConfig;

    public final IntegerConfig integerConfig;
    public final StringConfig stringConfig;

    public final ListConfig<String> listConfig;

    public final MapConfig<String, String> mapConfig;

    public TestConfig(ConfigCreator creator) {
        this.stringConfig = creator.createString("string", null);
        this.booleanConfig = creator.createBoolean("boolean", true);
        this.doubleConfig = creator.createDouble("double", 0.0);
        this.floatConfig = creator.createFloat("float", 0.0F);
        this.integerConfig = creator.createInteger("integer", 0);
        this.enumConfig = creator.createEnum("enum", TestEnum.GOOD, TestEnum.class);
        this.listConfig = creator.createList("list", List.of("Hello World", "Hello World Again").toArray(new String[0]), String[].class);
        Map<String, String> testMap = new HashMap<>();
        testMap.put("Hello", "World");
        testMap.put("Hello2", "World2");
        this.mapConfig = creator.createMap("map", testMap);
    }

}
