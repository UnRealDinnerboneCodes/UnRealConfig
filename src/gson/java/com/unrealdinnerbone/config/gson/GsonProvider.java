package com.unrealdinnerbone.config.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unrealdinnerbone.config.api.ClassMapper;
import com.unrealdinnerbone.config.api.ConfigID;
import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GsonProvider implements IProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(GsonProvider.class);
    private JsonObject jsonObject;
    private final Gson gson;
    private final Path path;

    public GsonProvider(Path path, Gson gson)  {
        this.path = path;
        this.gson = gson;
    }

    @Override
    public void invalidateCache() {
        jsonObject = null;
    }

    @Override
    public <T> @Nullable T get(ConfigID id, Class<T> tClass, ClassMapper<T> mapper) throws ConfigParseException, ConfigNotFoundException {
        if(jsonObject == null) {
            try {
                if(!Files.exists(path)) {
                    Files.writeString(path, gson.toJson(new JsonObject()));
                }
                String jsonString = Files.readString(path);
                JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
                if(jsonObject == null) {
                    throw new ConfigParseException("Could not parse json");
                }else {
                    this.jsonObject = jsonObject;
                }
            } catch (IOException e) {
                throw new ConfigParseException("Could not read file: " + e.getMessage());
            }
        }
        if(jsonObject.has(id.key())) {
            JsonElement jsonElement = jsonObject.get(id.key());
            if(jsonElement instanceof JsonObject jObject) {
                if(jObject.has(id.value())) {
                    return gson.fromJson(jObject.get(id.value()), tClass);
                }else {
                    throw new ConfigNotFoundException("Could not find object in json with id " + id.value());
                }
            }else {
                throw new ConfigParseException(id.key() + " is not a JsonObject");
            }
        }else {
            throw new ConfigNotFoundException("Could not find object in json with id" + id.key());
        }
    }
    @Override
    public <T> boolean save(@NotNull ConfigID id, Class<T> tClass, T value) throws ConfigParseException {
        if(!jsonObject.has(id.key())) {
            jsonObject.add(id.key(), new JsonObject());
        }
        JsonElement jsonElement = jsonObject.get(id.key());
        if(jsonElement instanceof JsonObject jObject) {
            jObject.add(id.value(), gson.toJsonTree(value));
            try {
                Files.writeString(path, gson.toJson(jsonObject));
            } catch (IOException e) {
                throw new ConfigParseException("Could not save config " + id.key());
            }
            return true;
        }else {
            throw new ConfigParseException("Could not save config " + id.key() + " as it is not a JsonObject");
        }
    }

    @Override
    public <T> void onConfigCreated(ConfigValue<T> configValue) {
        try {
            if (configValue.get().isEmpty()) {
                save(configValue.getId(), configValue.getClassType(), configValue.getDefaultValue());
            }
        }catch (ConfigParseException e) {
            LOGGER.error("Could not save config " + configValue.getId().key() + " as it is not a JsonObject");
        }
    }
}
