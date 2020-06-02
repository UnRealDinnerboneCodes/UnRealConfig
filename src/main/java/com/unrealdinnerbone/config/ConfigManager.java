package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.IConfig;
import com.unrealdinnerbone.config.api.provider.IProvider;
import com.unrealdinnerbone.config.impl.provider.EnvProvider;
import com.unrealdinnerbone.config.impl.provider.FileProvider;
import com.unrealdinnerbone.config.lib.ConfigUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConfigManager {

    private final List<IProvider> providers;
    private boolean doSave;

    public ConfigManager() {
        this.providers = new ArrayList<>();
        this.doSave = true;
    }

    public ConfigManager addProvider(IProvider provider) {
        this.providers.add(provider);
        return this;
    }


    public <T extends IConfig> T saveConfig(T iConfig) {
        if(doSave) {
            Arrays.stream(iConfig.getClass().getDeclaredFields()).forEach(field -> {
                Object value = ConfigUtils.getValueOfField(field, iConfig);
                providers.stream()
                        .filter(IProvider::canSave).
                        forEach(provider -> provider.save( iConfig.getFolderName(), iConfig.getName(), field.getName(),  value));
            });
        }
        return iConfig;
    }


    public <T extends IConfig> IConfig[] loadConfigs(T... iConfigs) {
        Arrays.stream(iConfigs).forEach(this::loadConfig);
        return iConfigs;
    }

    public <T extends IConfig> IConfig loadConfig(T iConfig) {
        Arrays.stream(iConfig.getClass().getDeclaredFields()).forEach(field ->
                 findValueFor().stream()
                         .map(provider -> provider.get(iConfig.getFolderName(), iConfig.getName(), field.getName()))
                .filter(Objects::nonNull)
                .findFirst().ifPresent(o -> ConfigUtils.setValueIfField(field, iConfig, o)));
        return iConfig;
    }

    private ArrayList<IProvider> findValueFor() {
        return providers.stream().filter(IProvider::canRead).collect(Collectors.toCollection(ArrayList::new));
    }

    public ConfigManager doSaving(boolean doSave) {
        this.doSave = doSave;
        return this;
    }

    public static ConfigManager createSimplePropertyConfigManger() {
        return new ConfigManager().doSaving(false).addProvider(FileProvider.FILE_PROVIDER).addProvider(EnvProvider.ENV_PROVIDER);
    }
    public static ConfigManager createSimpleEnvPropertyConfigManger() {
        return new ConfigManager().doSaving(false).addProvider(EnvProvider.ENV_PROVIDER);
    }
}