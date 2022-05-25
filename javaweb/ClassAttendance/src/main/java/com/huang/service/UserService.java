package com.huang.service;

import com.huang.model.response.ResponseModel;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface UserService {

    /**
     * 授权登录
     * @param code
     * @return
     * @throws WxErrorException
     * @throws IOException
     */
    ResponseModel auth2Login(String code) throws WxErrorException, IOException;

    /**
     * 实名认证
     * @param openId
     * @param userId
     * @param username
     * @param identity
     * @return
     */
    ResponseModel verified(String openId, String userId, String username, int identity);

    /**
     * 获得用户个人信息
     * @param openId
     * @return
     */
    ResponseModel getUserInfo(String openId);

    /**
     * 老师得到所有授教课程
     * @param openId
     * @return
     */
    ResponseModel teacherCourses(String openId);

}
