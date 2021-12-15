package com.unrealdinnerbone.config.api;

import java.util.Optional;

public interface IProvider {

    <T> Optional<Object> get(Namespace id, T defaultValue);

    default <T> boolean save(Namespace id, T value) {
        return false;
    }

    default <T> void setDefault(Namespace id, T defaultValue) {};

}
