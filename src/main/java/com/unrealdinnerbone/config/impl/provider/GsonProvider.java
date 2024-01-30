package com.unrealdinnerbone.config.impl.provider;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unrealdinnerbone.config.api.ConfigCreator;
import com.unrealdinnerbone.config.api.Provider;
import com.unrealdinnerbone.config.api.exception.ConfigException;
import com.unrealdinnerbone.config.api.exception.ConfigLoadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class GsonProvider extends Provider {
    private final Path path;
    private final boolean loadExtra;
    private final boolean alertSaveBeforeRead;

    private boolean hasRead = false;
    public GsonProvider(Path path, Gson gson)  {
        super(gson);
        this.path = path;
        this.loadExtra = true;
        this.alertSaveBeforeRead = true;
    }

    public GsonProvider(Path path, Gson gson, boolean loadExtra, boolean alertSaveBeforeRead)  {
        super(gson);
        this.path = path;
        this.loadExtra = loadExtra;
        this.alertSaveBeforeRead = alertSaveBeforeRead;
    }

    @Override
    public void read() throws ConfigException {
        if (!Files.exists(path)) {
            hasRead = true;
            save();
        }
        JsonObject jsonObject = gson.fromJson(readString(path), JsonObject.class);
        configCategory.fromJsonElement(gson, jsonObject);
        hasRead = true;
    }


    @Override
    public boolean save() throws ConfigException {
        if(alertSaveBeforeRead && !hasRead) {
            throw new ConfigLoadException("You must read the config before you save it");
        }
        JsonObject jsonElement = configCategory.asJsonElement(gson).getAsJsonObject();
        if(loadExtra && Files.exists(path)) {
            JsonObject readValue = gson.fromJson(readString(path), JsonObject.class);
            jsonElement = mergeObject(readValue, jsonElement);
        }
        writeString(path, gson.toJson(jsonElement));
        return true;
    }


    private static String readString(Path path) throws ConfigException {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new ConfigLoadException("Could not read config at " + path, e);
        }
    }

    private static void writeString(Path path, String string) throws ConfigException {
        try {
            Files.writeString(path, string);
        } catch (IOException e) {
            throw new ConfigLoadException("Could not write config at " + path, e);
        }
    }


    public static JsonObject mergeObject(JsonObject into, JsonObject merging) {
        JsonObject newObject = into.deepCopy();
        for (String overrideKey : merging.keySet()) {
            JsonElement element = merging.get(overrideKey);

            if (element.isJsonObject()) {
                JsonElement original = into.get(overrideKey);
                if (original != null && original.isJsonObject()) {
                    mergeObject(original.getAsJsonObject(), element.getAsJsonObject());
                } else {
                    newObject.add(overrideKey, element);
                }
            } else {
                newObject.add(overrideKey, element);
            }
        }
        return newObject;
    }



}
