package com.eardh.chatroom.utils;

public class ValidUtil {

    public static boolean validIP(String ip) {
        String trim = ip.trim();
        String[] strings = trim.split("\\.");
        if (trim.length() == 0 || strings.length != 4) {
            return false;
        }
        for (String string : strings) {
            try {
                int i = Integer.parseInt(string);
                if (i > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
