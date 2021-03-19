package com.unrealdinnerbone.config.impl.provider;

import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.json.JsonFormat;
import com.unrealdinnerbone.config.api.provider.IProvider;
import com.unrealdinnerbone.config.lib.ConfigUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class GsonFileProvider implements IProvider {

    public static final IProvider PROVIDER = new GsonFileProvider();

    private static final ConfigFormat<?> JSON_FORMAT = JsonFormat.fancyInstance();

    @Override
    public Optional<Object> get(String storeLocation, String configName, String value) {
        try {
            Path configPath = ConfigUtils.getOrCreateFile(storeLocation, configName + ".json");
            ConfigUtils.initEmpty(configPath, JsonFormat.fancyInstance());
            FileConfig fileConfig = FileConfig.of(configPath, JsonFormat.fancyInstance());
            fileConfig.load();
            return fileConfig.getOptional(value);
        } catch(IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean save(String storeLocation, String configName, String value, Object object) {
        try {
            Path configFile = ConfigUtils.getOrCreateFile(storeLocation, configName + ".json");
            ConfigUtils.initEmpty(configFile, JsonFormat.fancyInstance());
            FileConfig fileConfig = FileConfig.of(configFile, JSON_FORMAT);
            fileConfig.load();
            fileConfig.set(value, object);
            fileConfig.save();
            return true;
        }catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean canSave() {
        return true;
    }
}
