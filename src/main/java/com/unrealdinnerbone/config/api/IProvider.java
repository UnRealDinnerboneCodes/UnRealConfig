package com.unrealdinnerbone.config.api;

import java.util.Optional;

public interface IProvider {

    <T> Optional<Object> get(ID id, T defaultValue);

    default <T> boolean save(ID id, T value) {
        return false;
    }

    default <T> void setDefault(ID id, T defaultValue) {};

}
