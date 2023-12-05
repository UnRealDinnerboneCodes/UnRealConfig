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

    public GsonProvider(Path path, Gson gson)  {
        super(gson);
        this.path = path;
    }

    @Override
    public void read() throws ConfigException {
        if (!Files.exists(path)) {
            save();
        }
        JsonObject jsonObject = gson.fromJson(readString(path), JsonObject.class);
        configCategory.fromJsonElement(gson, jsonObject);
    }


    @Override
    public boolean save() throws ConfigException {
        writeString(path, gson.toJson(configCategory.asJsonElement(gson)));
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



}
