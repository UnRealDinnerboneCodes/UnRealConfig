package com.unrealdinnerbone.config.api.provider;

import java.util.Optional;

public interface IProvider {

    Optional<Object> get(String storeLocation, String configName, String value);

    default boolean save(String storeLocation, String configName, String value, Object object) {
        return false;
    }

    default boolean canSave() {
        return false;
    }

    default boolean canRead() {
        return true;
    }
}
