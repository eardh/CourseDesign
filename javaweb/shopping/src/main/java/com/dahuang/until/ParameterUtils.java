package com.dahuang.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dahuang.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/10 10:51
 */
public class ParameterUtils {

    /**
     * 将Http中的参数映射到对象
     * @author dahuang
     * @date 2021/6/10 15:46
     * @param clazz
     * @param request
     * @return java.lang.Object
     */
    public static Object RequestParamToObj(Class<?> clazz, HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<>();
        request.getParameterMap().entrySet().stream().forEach( k -> {
           paramMap.put(k.getKey(),k.getValue()[0]);
        });

        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), clazz);
    }

}
