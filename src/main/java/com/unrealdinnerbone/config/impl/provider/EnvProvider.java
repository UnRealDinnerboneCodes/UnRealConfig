package com.unrealdinnerbone.config.impl.provider;

import com.unrealdinnerbone.config.api.provider.IProvider;

public class EnvProvider implements IProvider {

    public static final IProvider ENV_PROVIDER = new EnvProvider();

    @Override
    public Object get(String storeLocation, String configName, String value) {
        return System.getenv().getOrDefault(storeLocation + "." + configName + "." + value, null);
    }
}
