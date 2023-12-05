package com.unrealdinnerbone.test;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.impl.provider.GsonProvider;
import com.unrealdinnerbone.test.data.TestConfig;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

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
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        gsonProvider.read();
        ConfigTest.assetConfigDefaultValues(testConfig, true);
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
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonBoolean() throws IOException {
        Path path = fileSystem.getPath("test_json_boolean.json");
        Files.writeString(path, " {\"boolean\": \"talse\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertDoesNotThrow(gsonProvider::read);
    }

    @Test
    public void testInvalidJsonDouble() throws IOException {
        Path path = fileSystem.getPath("test_json_double.json");
        Files.writeString(path, "{\"double\": {\"hello\": \"there\"}}");
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonFloat() throws IOException {
        Path path = fileSystem.getPath("test_json_float.json");
        Files.writeString(path, "{\"float\": [1.0, 1.0]}");
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonEnum() throws IOException, ConfigException {
        Path path = fileSystem.getPath("test_json_enum.json");
        Files.writeString(path, "{\"enum\": \"OK\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        gsonProvider.read();
        Assertions.assertNull(testConfig.enumConfig.get());
    }

    @Test
    public void testInvalidJsonInt() throws IOException {
        Path path = fileSystem.getPath("test_json_int.json");
        Files.writeString(path, "{\"integer\": \"BAD\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonList() throws IOException {
        Path path = fileSystem.getPath("test_json_list.json");
        Files.writeString(path, "{\"list\": \"OK\"}");
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }

    @Test
    public void testInvalidJsonMap() throws IOException {
        Path path = fileSystem.getPath("test_json_map.json");
        Files.writeString(path, "{\"map\": [\"LIST?\"]}");
        GsonProvider gsonProvider = new GsonProvider(path, gson);
        TestConfig testConfig = gsonProvider.loadConfig(TestConfig::new);
        Assertions.assertThrows(ConfigException.class, gsonProvider::read);
    }



}
