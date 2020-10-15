package com.unrealdinnerbone.config.impl.provider;

import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.json.JsonFormat;
import com.unrealdinnerbone.config.api.provider.IProvider;
import com.unrealdinnerbone.config.lib.ConfigUtils;

import java.io.File;
import java.util.Optional;

public class FileProvider implements IProvider {

    public static final IProvider FILE_PROVIDER = new FileProvider();

    private static final ConfigFormat<?> JSON_FORMAT = JsonFormat.fancyInstance();


    @Override
    public Optional<Object> get(String storeLocation, String configName, String value) {
        File configFile = ConfigUtils.getOrCreateFile(storeLocation, configName + ".json");
        ConfigUtils.initEmpty(configFile, JsonFormat.fancyInstance());
        FileConfig fileConfig = FileConfig.of(configFile, JsonFormat.fancyInstance());
        fileConfig.load();
        return fileConfig.getOptional(value);
    }


    @Override
    public boolean save(String storeLocation, String configName, String value, Object object) {
        File configFile = ConfigUtils.getOrCreateFile(storeLocation, configName + ".json");
        ConfigUtils.initEmpty(configFile, JsonFormat.fancyInstance());
        FileConfig fileConfig = FileConfig.of(configFile, JSON_FORMAT);
        fileConfig.load();
        fileConfig.set(value, object);
        fileConfig.save();
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }
}
