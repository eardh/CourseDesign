package com.eardh.utils;

public class StringUtil {

    public static String urlPrefix(String url) {
        int length = url.length();
        int count = 0, i = 0;
        for (; i < length; i++) {
            if (url.charAt(i) == '/') {
                count++;
                if (count == 2) {
                    break;
                }
            }
        }
        return url.substring(0, i);
    }
}
