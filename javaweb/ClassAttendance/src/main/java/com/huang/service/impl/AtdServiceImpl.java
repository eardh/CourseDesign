package com.huang.service.impl;

import com.huang.mapper.AtdMapper;
import com.huang.mapper.UserMapper;
import com.huang.model.entity.Course;
import com.huang.model.entity.SignInfo;
import com.huang.model.entity.User;
import com.huang.model.enums.ResponseCode;
import com.huang.model.response.ResponseModel;
import com.huang.service.AtdService;
import com.huang.util.DisUtils;
import com.huang.util.RedisUtil;
import me.chanjar.weixin.common.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AtdServiceImpl implements AtdService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AtdMapper atdMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseModel teacherCHistory(String openId) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        List<SignInfo> signInfos = atdMapper.queryByTeacherId(user.getUserId());
        return ResponseModel.success(signInfos);
    }

    @Override
    public ResponseModel studentCHistory(String openId) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        List<SignInfo> signInfos = atdMapper.queryByStudentId(user.getUserId());
        return ResponseModel.success(signInfos);
    }

    @Override
    public ResponseModel studentSign(String openId, String codeSign, double longitude, double latitude) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        List<Course> courses = userMapper.queryCourseByStudentId(user.getUserId());
        for (Course c : courses) {
            String s = "courseId-" + c.getCourseId();
            if (redisUtil.hasKey(s)) {
                Map<Object, Object> hmget = redisUtil.hmget(s);
                int signId = Integer.parseInt(String.valueOf(hmget.get("signId")));
                if (atdMapper.queryIsSign(signId, user.getUserId()) != null){
                    return ResponseModel.fail(ResponseCode.FAILURE)
                            .setMessage("已经签到过了");
                }
                String signCode = String.valueOf(hmget.get("signCode"));
                double longitude1 = Double.parseDouble(String.valueOf(hmget.get("longitude")));
                double latitude1 = Double.parseDouble(String.valueOf(hmget.get("latitude")));
                double distance = DisUtils.GetDistance(latitude, longitude, latitude1, longitude1);
                if (!signCode.equals(codeSign) ||  distance > 500) {
                    return ResponseModel.fail(ResponseCode.FAILURE)
                            .setMessage("签到失败");
                }
                atdMapper.insertRecordSign(user.getUserId(), signId);
                return ResponseModel.success()
                        .setMessage("签到成功");
            }
        }
        return ResponseModel.success()
                .setMessage("暂无签到信息");
    }

    @Override
    public ResponseModel issueSignByCode(String openId, Map<String, Object> params) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        String signCode = RandomUtils.getRandomStr().substring(0, 4);
        long startTime = System.currentTimeMillis();
        params.put("signId", null);
        params.put("signCode", signCode);
        params.put("startTime", startTime);
        params.put("signType", 0);
        params.put("signLocation", params.get("longitude") + "|" + params.get("latitude"));
        atdMapper.insertSign(params);

        Map<String, Object> map = new HashMap<>();
        map.put("longitude", params.get("longitude"));
        map.put("latitude", params.get("latitude"));
        map.put("signCode", signCode);
        map.put("signId", params.get("signId"));
        String key = "courseId-" + params.get("courseId").toString();
        redisUtil.hmset(key, map);
        redisUtil.expire(key, Long.parseLong(String.valueOf(params.getOrDefault("signDuring", 1))) * 60);
        return ResponseModel.success(signCode)
                .setMessage("发起签到成功");
    }

    @Override
    public ResponseModel signDetails(String openId, int signId) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        List<User> users = atdMapper.queryStusBySignId(signId);
        for (User u : users) {
            if (atdMapper.queryIsSign(signId, u.getUserId()) != null) {
                u.setSign(true);
            }
        }
        return ResponseModel.success(users);
    }

    @Override
    public ResponseModel signInfos(String openId, int signId) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        SignInfo signInfo = atdMapper.queryInfoBySignId(signId);
        return ResponseModel.success(signInfo);
    }

    @Override
    public ResponseModel fixSign(String openId, String studentId, int signId) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        atdMapper.insertRecordSign(studentId, signId);
        return ResponseModel.success()
                .setMessage("补签成功");
    }

    @Override
    public ResponseModel alreadySign(String openId, int signId) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        return ResponseModel.success(atdMapper.queryIsSign(signId, user.getUserId()) != null);
    }
}
