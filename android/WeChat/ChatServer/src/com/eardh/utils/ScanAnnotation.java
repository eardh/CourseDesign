package com.eardh.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.*;

public class ScanAnnotation {

    public static List<Class<?>> annotatedClass(Class<? extends Annotation> annotation) {
        ScanAnnotation scanFileUtil = new ScanAnnotation();
        return scanFileUtil.searchClasses("", annotation);
    }

    private List<Class<?>> searchClasses(String classPath, Class<? extends Annotation> annotation){
        List<Class<?>> classes = new ArrayList<>();
        try {
            URI uri = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(classPath.replace(".", "/"))).toURI();
            findClassWithAnnotation(classes, uri, classPath, annotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private void findClassWithAnnotation(List<Class<?>> list, URI uri, String packName, Class<? extends Annotation> annotation) {
        File file = new File(uri);
        file.listFiles(chiFile -> {
            String s = packName.isEmpty() ? "" : (packName + ".");
            if (chiFile.isDirectory()) {
                String subPackName = s + chiFile.getName();
                try {
                    URI subUrl = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(subPackName.replace(".", "/"))).toURI();
                    findClassWithAnnotation(list, subUrl, subPackName, annotation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (chiFile.getName().endsWith(".class")) {
                Class<?> clazz = null;
                try {
                    clazz = ClassLoader.getSystemClassLoader().loadClass(s + chiFile.getName().replace(".class", ""));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                assert clazz != null;
                Annotation anno = clazz.getAnnotation(annotation);
                if (anno != null) {
                    list.add(clazz);
                }
            }
            return true;
        });
    }
}