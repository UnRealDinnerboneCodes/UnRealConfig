module com.unrealdinnerbone.unrealconfig.gson {
    requires com.unrealdinnerbone.unrealconfig;
    requires com.google.gson;
    requires static org.jetbrains.annotations;
    requires org.slf4j;
    exports com.unrealdinnerbone.config.gson;
}