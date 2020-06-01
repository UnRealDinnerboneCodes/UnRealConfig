package com.unrealdinnerbone.config;

import com.unrealdinnerbone.config.api.IConfig;
import com.unrealdinnerbone.config.api.provider.IProvider;
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

    public void addProvider(IProvider provider) {
        this.providers.add(provider);
    }


    public void saveConfig(IConfig iConfig) {
        if(doSave) {
            Arrays.stream(iConfig.getClass().getDeclaredFields()).forEach(field -> {
                Object value = ConfigUtils.getValueOfField(field, iConfig);
                providers.stream()
                        .filter(IProvider::canSave).
                        forEach(provider -> provider.save( iConfig.getFolderName(), iConfig.getName(), field.getName(),  value));
            });
        }
    }

    public void loadConfig(IConfig iConfig) {
        Arrays.stream(iConfig.getClass().getDeclaredFields()).forEach(field ->
                 findValueFor().stream()
                         .map(provider -> provider.get(iConfig.getFolderName(), iConfig.getName(), field.getName()))
                .filter(Objects::nonNull)
                .findFirst().ifPresent(o -> ConfigUtils.setValueIfField(field, iConfig, o)));
    }

    private ArrayList<IProvider> findValueFor() {
        return providers.stream().filter(IProvider::canRead).collect(Collectors.toCollection(ArrayList::new));
    }

    public void doSaving(boolean doSave) {
        this.doSave = doSave;
    }
}