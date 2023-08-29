package com.unrealdinnerbone.config.gson.test;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.config.gson.GsonProvider;
import com.unrealdinnerbone.unreallib.json.JsonUtil;
import com.unrealdinnerbone.unreallib.json.gson.GsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class GsonConfigTests {

    private static FileSystem fileSystem;

    @BeforeAll
    public static void setup() throws IOException {
        fileSystem = MemoryFileSystemBuilder.newEmpty().build("test");
    }

    @Test
    public void testBasicConfig() throws ConfigException, IOException {
        Path path = fileSystem.getPath("test.json");
        GsonParser provider = JsonUtil.createParser(gsonBuilder -> gsonBuilder);
        String testConfig = """
                {
                    "test": {
                        "string": "Hello World",
                        "boolean": true,
                        "double": 1.0,
                        "float": 1.0,
                        "integer": 1,
                        "enum": "GOOD",
                        "list": [
                            "Hello World",
                            "Hello World Again"
                        ]
                    }
                }
                """;

        Files.writeString(path, testConfig);
        ConfigManager configManager = new ConfigManager(new GsonProvider(path, provider));
        TestConfig testConfig1 = configManager.loadConfig("test", TestConfig::new);
        Assertions.assertEquals("Hello World", testConfig1.stringConfig.getExceptionally());
        Assertions.assertEquals(true, testConfig1.booleanConfig.getExceptionally());
        Assertions.assertEquals(1.0, testConfig1.doubleConfig.getExceptionally());
        Assertions.assertEquals(1.0F, testConfig1.floatConfig.getExceptionally());
        Assertions.assertEquals(1, testConfig1.integerConfig.getExceptionally());
        Assertions.assertEquals(TestEnum.GOOD, testConfig1.enumConfig.getExceptionally());
        Assertions.assertTrue(Arrays.stream(testConfig1.listConfig.getExceptionally()).toList().containsAll(List.of("Hello World", "Hello World Again")));

        testConfig1.booleanConfig.set(false);
        testConfig1.booleanConfig.save();
        TestConfig testConfig2 = configManager.loadConfig("test", TestConfig::new);
        Assertions.assertEquals(false, testConfig2.booleanConfig.getExceptionally());
    }

}
