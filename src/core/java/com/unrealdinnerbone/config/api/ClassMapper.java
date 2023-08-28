package com.unrealdinnerbone.config.api;

import com.unrealdinnerbone.config.exception.ConfigParseException;
public interface ClassMapper<T> {

    <C> T map(Class<C> t, C value) throws ConfigParseException;

}