package com.unrealdinnerbone.config.lib;

import com.electronwill.nightconfig.core.ConfigFormat;
import com.unrealdinnerbone.config.api.IConfig;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtils {

    public static Object getValueOfField(Field field, Object object) {
        try {
            if(!field.canAccess(object)) {
                field.setAccessible(true);
            }
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setValueIfField(Field field, IConfig classObject, Object object){
        try {
            if(!field.canAccess(classObject)) {
                field.setAccessible(true);
            }
            field.set(classObject, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void initEmpty(Path path, ConfigFormat<?> format) throws IOException {
        if(!Files.exists(path)) {
            Files.createFile(path);
        }
        if(Files.readString(path).isEmpty()) {
            format.initEmptyFile(path);
        }
    }

    public static Path getOrCreateFile(String name, String fileName) throws IOException {
        Path path = Path.of(name);
        if(!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path path1 = Path.of(name, fileName);
        if(!Files.exists(path1)) {
            Files.createFile(path1);
        }
        return path1;
    }

}
