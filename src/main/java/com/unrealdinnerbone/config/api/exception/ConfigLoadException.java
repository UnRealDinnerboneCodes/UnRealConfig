package com.unrealdinnerbone.config.api.exception;

public class ConfigLoadException extends ConfigException {

    public ConfigLoadException(String message) {
        super(message);
    }

    public ConfigLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
