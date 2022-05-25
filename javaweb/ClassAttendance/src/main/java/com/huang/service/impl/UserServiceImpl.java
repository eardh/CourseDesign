package com.huang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.huang.mapper.UserMapper;
import com.huang.model.entity.Course;
import com.huang.model.entity.User;
import com.huang.model.enums.ResponseCode;
import com.huang.model.response.ResponseModel;
import com.huang.service.UserService;
import com.huang.util.JsonUtils;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.enums.WxType;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static me.chanjar.weixin.mp.enums.WxMpApiUrl.OAuth2.OAUTH2_USERINFO_URL;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WxMpService wxService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseModel auth2Login(String code) throws WxErrorException, IOException {
        WxOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
        String url = String.format(OAUTH2_USERINFO_URL.getUrl(wxService.getWxMpConfigStorage()), accessToken.getAccessToken(), accessToken.getOpenId(), "zh_CN");
        RequestExecutor<String, String> executor = SimpleGetRequestExecutor.create(wxService.getRequestHttp());
        JSONObject jsonObject = JSONObject.parseObject(executor.execute(url, null, WxType.MP));

        User user = userMapper.queryByOpenId(jsonObject.getString("openid"));
        if (user == null) {
            user = new User();
            user.setOpenId(jsonObject.getString("openid"));
            user.setWxNickname(jsonObject.getString("nickname"));
            user.setSex(jsonObject.getIntValue("sex"));
            user.setAvatarImg(jsonObject.getString("headimgurl"));
            userMapper.insertUser(user);
        }
        return ResponseModel.success(user)
                .setMessage("获取用户信息成功");
    }

    @Override
    public ResponseModel verified(String openId, String userId, String username, int identity) {
        if (userMapper.queryEducation(userId, username, identity) == null) {
            return ResponseModel.fail(ResponseCode.FAILURE)
                    .setMessage("身份验证失败");
        }
        userMapper.updateByOpenId(openId, userId, username, identity);
        return ResponseModel.success()
                .setMessage("认证成功");
    }

    @Override
    public ResponseModel getUserInfo(String openId) {
        User user = userMapper.queryByOpenId(openId);
        return ResponseModel.success(user);
    }

    @Override
    public ResponseModel teacherCourses(String openId) {
        User user = userMapper.queryByOpenId(openId);
        if (user.getUserId() == null) {
            return ResponseModel.fail(ResponseCode.USER_UNKONWN_INDENTITY)
                    .setMessage("用户未实名");
        }
        List<Course> courses = userMapper.queryCoursesByTeacher(user.getUserId());
        return ResponseModel.success(courses);
    }
}
