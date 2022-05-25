package com.huang;

import com.huang.mapper.AtdMapper;
import com.huang.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ClassAttendanceApplicationTests {

    @Autowired
    private AtdMapper atdMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() throws InterruptedException {
        Map<String, Object> params = new HashMap<>();
        params.put("signId", null);
        params.put("courseId", "111111");
        params.put("signCode", "222222");
        params.put("startTime", 33333);
        params.put("signType", 0);
        params.put("longitude", 89.0);
        params.put("latitude", 90.1);
        params.put("signLocation", "111111" + "|fdsfsdfsdf");
        params.put("signDuring", 1);
        atdMapper.insertSign(params);

        Map<String, Object> map = new HashMap<>();
        map.put("longitude", params.get("longitude"));
        map.put("latitude", params.get("latitude"));
        map.put("signCode", params.get("signCode"));
        map.put("signId", params.get("signId"));
        redisUtil.hmset("courseId" + params.get("courseId").toString(), map);

        Map<Object, Object> courseId = redisUtil.hmget("courseId" + params.get("courseId").toString());

        for (Map.Entry<Object, Object> entry : courseId.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        redisUtil.expire("courseId" + params.get("courseId").toString(), 40);

        TimeUnit.SECONDS.sleep(41);

        courseId = redisUtil.hmget("courseId" + params.get("courseId").toString());

        for (Map.Entry<Object, Object> entry : courseId.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

}
