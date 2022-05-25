package com.eardh.utils;

import com.eardh.annotation.EarController;
import com.eardh.annotation.EarMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantPool {

    public static final Map<String, Method> methodMap = new HashMap<>();
    public static final Map<String, Object> objectMap = new HashMap<>();
    public static final Map<String, Annotation[][]> paramMap = new HashMap<>();

    static {
        List<Class<?>> classes = ScanAnnotation.annotatedClass(EarController.class);
        for (Class<?> aClass : classes) {
            StringBuilder url = new StringBuilder();
            EarMapping earMapping = aClass.getAnnotation(EarMapping.class);
            url.append(earMapping.value());
            try {
                Constructor<?> constructor = aClass.getConstructor();
                Object o = constructor.newInstance();
                objectMap.put(url.toString(), o);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                EarMapping annotation = method.getAnnotation(EarMapping.class);
                String s = url + annotation.value();
                methodMap.put(s, method);
                paramMap.put(s, method.getParameterAnnotations());
            }
        }
    }
}
