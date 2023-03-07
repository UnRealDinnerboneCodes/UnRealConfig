package com.unrealdinnerbone.config.api;

import com.unrealdinnerbone.unreallib.Namespace;

import java.util.Optional;

public interface IProvider {

    Optional<Object> get(Namespace id);

    default <T> boolean save(Namespace id, T value) {
        return false;
    }

    default <T> void setDefault(Namespace id, T defaultValue) {};

}
