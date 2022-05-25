package com.huang.service;

import com.huang.model.response.ResponseModel;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface AtdService {

    /**
     * 老师查看发布的签到历史情况
     * @param openId
     * @return
     */
    ResponseModel teacherCHistory(String openId);

    /**
     * 查询学生历史签到记录
     * @param openId
     * @return
     */
    ResponseModel studentCHistory(String openId);

    /**
     * 学生签到
     * @param openId
     * @param codeSign
     * @return
     */
    ResponseModel studentSign(String openId, String codeSign, double longitude, double latitude);

    /**
     * 发起签到
     * @param params
     * @return
     */
    ResponseModel issueSignByCode(String openId, Map<String, Object> params);

    /**
     * 签到详细信息
     * @param openId
     * @param signId
     * @return
     */
    ResponseModel signDetails(String openId, int signId);

    /**
     * 查询签到信息
     * @param openId
     * @param signId
     * @return
     */
    ResponseModel signInfos(String openId, int signId);

    /**
     * 补签
     * @param openId
     * @param studentId
     * @param signId
     * @return
     */
    ResponseModel fixSign(String openId, String studentId, int signId);

    ResponseModel alreadySign(String openId, int signId);
}
