package com.unrealdinnerbone.config.lib;

import com.unrealdinnerbone.config.api.IConfig;

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
}
