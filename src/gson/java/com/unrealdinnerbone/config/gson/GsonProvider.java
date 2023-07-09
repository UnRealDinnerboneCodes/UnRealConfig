package com.unrealdinnerbone.config.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unrealdinnerbone.config.api.ConfigValue;
import com.unrealdinnerbone.config.api.IProvider;
import com.unrealdinnerbone.config.exception.ConfigNotFoundException;
import com.unrealdinnerbone.config.exception.ConfigParseException;
import com.unrealdinnerbone.unreallib.LogHelper;
import com.unrealdinnerbone.unreallib.Namespace;
import com.unrealdinnerbone.unreallib.json.gson.GsonParser;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GsonProvider implements IProvider {

    private static final Logger LOGGER = LogHelper.getLogger();
    private final JsonObject jsonObject;
    private final Gson gson;
    public GsonProvider(Path path, GsonParser gsonParser) throws ConfigParseException, IOException {
        String jsonString = Files.readString(path);
        JsonObject jsonObject = gsonParser.parse(JsonObject.class, jsonString);
        if(jsonObject == null) {
            throw new ConfigParseException("Could not parse json");
        }else {
            this.jsonObject = jsonObject;
            this.gson = gsonParser.getGson();
        }
    }
    @Override
    public <T> @Nullable T get(ConfigValue<T> value) throws ConfigParseException, ConfigNotFoundException {
        if(jsonObject.has(value.getId().key())) {
            JsonElement jsonElement = jsonObject.get(value.getId().key());
            if(jsonElement instanceof JsonObject jObject) {
                if(jObject.has(value.getId().value())) {
                    return gson.fromJson(jObject.get(value.getId().value()), value.getClassType());
                }else {
                    throw new ConfigNotFoundException("Could not find object in json with id " + value.getId().value());
                }
            }else {
                throw new ConfigParseException(value.getId().key() + " is not a JsonObject");
            }
        }else {
            throw new ConfigNotFoundException("Could not find object in json with id" + value.getId().key());
        }
    }
    @Override
    public <T> boolean save(Namespace id, Class<T> tClass, T value) throws ConfigParseException {
        if(!jsonObject.has(id.key())) {
            jsonObject.add(id.key(), new JsonObject());
        }
        JsonElement jsonElement = jsonObject.get(id.key());
        if(jsonElement instanceof JsonObject jObject) {
            jObject.add(id.value(), gson.toJsonTree(value));
            return true;
        }else {
            throw new ConfigParseException("Could not save config " + id.key() + " as it is not a JsonObject");
        }
    }
}
