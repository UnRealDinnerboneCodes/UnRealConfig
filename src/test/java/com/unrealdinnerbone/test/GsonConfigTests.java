package com.unrealdinnerbone.test;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unrealdinnerbone.config.api.ConfigCreator;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.config.ConfigValue;
import com.unrealdinnerbone.config.impl.provider.GsonProvider;
import com.unrealdinnerbone.test.data.TestConfig;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonConfigTests {

    private static FileSystem fileSystem;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private static Path path;

    @BeforeAll
    public static void setup() throws IOException {
        fileSystem = MemoryFileSystemBuilder.newEmpty().build("test");
        path = fileSystem.getPath("testone.json");
    }

    @Test
    public void testGson() throws ConfigException {
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        gsonProvider.read();
        ConfigTest.assetConfigDefaultValues(testConfig);
        ConfigTest.changeConfigValues(testConfig);
        ConfigTest.assertChangedConfigValues(testConfig, true);
        gsonProvider.save();
        GsonProvider newGsonProvider = new GsonProvider(path, gson);
        TestConfig newTestConfig = newGsonProvider.loadConfig(TestConfig::new);
        newGsonProvider.read();
        ConfigTest.assertChangedConfigValues(newTestConfig, true);
    }




    @Test
    public void testInvalidJsonSting() throws IOException {
        Path path = fileSystem.getPath("test_json_string.json");
        Files.writeString(path, "{\"string\": {\"bad\":\"value\"}}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonBoolean() throws IOException {
        Path path = fileSystem.getPath("test_json_boolean.json");
        Files.writeString(path, " {\"boolean\": \"talse\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertDoesNotThrow(gsonProvider::read);
    }

    @Test
    public void testInvalidJsonDouble() throws IOException {
        Path path = fileSystem.getPath("test_json_double.json");
        Files.writeString(path, "{\"double\": {\"hello\": \"there\"}}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonFloat() throws IOException {
        Path path = fileSystem.getPath("test_json_float.json");
        Files.writeString(path, "{\"float\": [1.0, 1.0]}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonEnum() throws IOException, ConfigException {
        Path path = fileSystem.getPath("test_json_enum.json");
        Files.writeString(path, "{\"enum\": \"OK\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        gsonProvider.read();
        Assertions.assertNull(testConfig.enumConfig.get());
    }

    @Test
    public void testInvalidJsonInt() throws IOException {
        Path path = fileSystem.getPath("test_json_int.json");
        Files.writeString(path, "{\"integer\": \"BAD\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonList() throws IOException {
        Path path = fileSystem.getPath("test_json_list.json");
        Files.writeString(path, "{\"list\": \"OK\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonMap() throws IOException {
        Path path = fileSystem.getPath("test_json_map.json");
        Files.writeString(path, "{\"map\": [\"LIST?\"]}");
        GsonProvider gsonProvider = new GsonProvider(path, gson, false);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }


    private static final String TEST_JSON = """
            {
              "config": "cake",
              "extra": {
                "config": "cake"
              }
            }""";
    @Test
    public void testExtraData() throws IOException, ConfigException {
        Path path1 = fileSystem.getPath("test_extra.json");
        GsonProvider gsonProvider = new GsonProvider(path1, gson);
        Files.writeString(path1, TEST_JSON);
        SimpleConfig simpleConfig = gsonProvider.loadConfig(SimpleConfig::new);
        gsonProvider.save();
        String s = Files.readString(path1);
        Assertions.assertEquals(TEST_JSON, s);
    }

    private static final String TEST_EXISTING_DATA = """
            {
              "enabled": false
            }""";

    private static final String TEST_EXISTING_DATA_WANTED = """
            {
              "enabled": true
            }""";
    @Test
    public void testExistingData() throws IOException, ConfigException {
        Path path1 = fileSystem.getPath("test_extra.json");
        GsonProvider gsonProvider = new GsonProvider(path1, gson);
        Files.writeString(path1, TEST_EXISTING_DATA);
        SimpleEnabled simpleConfig = gsonProvider.loadConfig(SimpleEnabled::new);
        simpleConfig.enabled.setValue(true);
        gsonProvider.save();
        String s = Files.readString(path1);
        Assertions.assertEquals(TEST_EXISTING_DATA_WANTED, s);
    }


    public static class SimpleConfig {

        private final ConfigValue<String> config;
        public SimpleConfig(ConfigCreator creator) {
            this.config = creator.createString("config", "test");
        }
    }

    public static class SimpleEnabled {

        private final ConfigValue<Boolean> enabled;


        public SimpleEnabled(ConfigCreator creator) {
            this.enabled = creator.createBoolean("enabled", true);
        }
    }


}
