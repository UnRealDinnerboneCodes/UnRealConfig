package com.unrealdinnerbone.config.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.unrealdinnerbone.config.api.exception.ConfigParseException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfigCategory extends ConfigValue<List<ConfigValue<?>>> {

    public ConfigCategory(String id, @NotNull List<ConfigValue<?>> configValues) {
        super(id, configValues);
    }

    @Override
    protected List<ConfigValue<?>> serialize(Gson gson, JsonElement jsonElement) throws ConfigParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (ConfigValue<?> configValue : get()) {
            String id = configValue.getId();
            JsonElement foundElement = getJsonElement(jsonObject, id);
            if (foundElement != null) {
                if (foundElement.isJsonNull()) {
                    configValue.setValue(null);
                } else {
                    configValue.fromJsonElement(gson, foundElement);
                }
            } else {
                if(configValue.get() != null) {
                    JsonElement jsonElement1 = configValue.asJsonElement(gson);
                    jsonObject.add(id, jsonElement1);
                }else {
                    jsonObject.add(id, JsonNull.INSTANCE);
                }
            }
        }
        return get();
    }

    private JsonElement getJsonElement(JsonObject jsonObject, String id) {
        if(id.isEmpty() || id.isBlank()) {
            return jsonObject;
        }else {
            return jsonObject.get(id);
        }
    }

    @Override
    public JsonElement deserialize(Gson gson, List<ConfigValue<?>> value) throws ConfigParseException {
        JsonObject jsonObject = new JsonObject();
        for (ConfigValue<?> configValue : value) {
            if(configValue.getId().isBlank()) {
                jsonObject = configValue.asJsonElement(gson).getAsJsonObject();
            }else {
                if(configValue.get() != null) {
                    jsonObject.add(configValue.getId(), configValue.asJsonElement(gson));
                }else {
                    jsonObject.add(configValue.getId(), JsonNull.INSTANCE);
                }
            }
        }
        return jsonObject;
    }

    public void addConfigValue(ConfigValue<?> configValue) {
        get().add(configValue);
    }



}
