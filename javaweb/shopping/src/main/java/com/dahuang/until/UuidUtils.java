package com.dahuang.until;

import java.util.UUID;

/**
 * @author dahuang
 * @date 2021/6/8 9:46
 */
public class UuidUtils {

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
