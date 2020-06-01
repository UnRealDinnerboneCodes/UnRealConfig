package com.unrealdinnerbone.config.lib;

import com.electronwill.nightconfig.core.ConfigFormat;
import com.unrealdinnerbone.config.api.IConfig;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

public class ConfigUtils {

    public static Object getValueOfField(Field field, Object object) {
        try {
            if(!field.isAccessible()) {
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
            if(!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(classObject, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void initEmpty(File configFile, ConfigFormat<?> format) {
        try {
            FileReader fileReader =  new FileReader(configFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if(bufferedReader.readLine() == null) {
                format.initEmptyFile(configFile);
            }
            bufferedReader.close();;
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getOrCreateFile(String name, String fileName) {
        File folder = new File(name);
        if(!folder.exists()) {
            folder.mkdir();
        }
        File theFile = new File(folder, fileName);
        if(!theFile.exists()) {
            try {
                theFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return theFile;
    }
}
