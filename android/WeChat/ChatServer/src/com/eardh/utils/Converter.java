package com.eardh.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Converter {

    public static Object convert(Class<?> clazz, ResultSet set) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        Constructor<?> constructor = clazz.getConstructor((Class<?>[]) null);
        Object object = constructor.newInstance();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Object value = set.getObject(fields[i].getName());
                fields[i].set(object, value.toString());
            } catch (SQLException | IllegalAccessException e) {
               //
            }
        }
        return object;
    }
}
