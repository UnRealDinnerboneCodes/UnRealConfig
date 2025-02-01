module com.unrealdinnerbone.unrealconfig {
    requires transitive com.google.gson;
    requires org.jetbrains.annotations;
    requires org.slf4j;
    exports com.unrealdinnerbone.config.api.exception;
    exports com.unrealdinnerbone.config.api;
    exports com.unrealdinnerbone.config.config;
    exports com.unrealdinnerbone.config.impl.provider;

}
