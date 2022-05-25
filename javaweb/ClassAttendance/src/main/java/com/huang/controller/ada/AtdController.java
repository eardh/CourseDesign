package com.huang.controller.ada;

import com.huang.model.response.ResponseModel;
import com.huang.service.AtdService;
import com.huang.service.UserService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/kq")
public class AtdController {

    @Autowired
    private WxMpService wxService;

    @Autowired
    private AtdService atdService;

    @RequestMapping(value = "/{appid}/teacher/issue_sign", method = RequestMethod.POST)
    public ResponseModel issueSignByCode(@PathVariable String appid,
                                         @RequestParam("openId") String openId,
                                         @RequestBody Map<String, Object> map) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.issueSignByCode(openId, map);
    }

    @RequestMapping(value = "/{appid}/student/sign_in", method = RequestMethod.POST)
    public ResponseModel studentSign(@PathVariable String appid,
                                     @RequestParam("openId") String openId,
                                     @RequestParam("codeSign") String codeSign,
                                     @RequestParam("longitude") double longitude,
                                     @RequestParam("latitude") double latitude) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.studentSign(openId, codeSign, longitude, latitude);
    }

    @RequestMapping(value = "/{appid}/teacher/sign_details", method = RequestMethod.POST)
    public ResponseModel signDetail(@PathVariable String appid,
                                     @RequestParam("openId") String openId,
                                     @RequestParam("signId") int signId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.signDetails(openId, signId);
    }

    @RequestMapping(value = "/{appid}/teacher/sign_info", method = RequestMethod.POST)
    public ResponseModel signInfo(@PathVariable String appid,
                                    @RequestParam("openId") String openId,
                                    @RequestParam("signId") int signId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.signInfos(openId, signId);
    }

    @RequestMapping(value = "/{appid}/student/sign_history", method = RequestMethod.POST)
    public ResponseModel signStuHistory(@PathVariable String appid,
                                    @RequestParam("openId") String openId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.studentCHistory(openId);
    }


    @RequestMapping(value = "/{appid}/teacher/sign_history", method = RequestMethod.POST)
    public ResponseModel signTecHistory(@PathVariable String appid,
                                        @RequestParam("openId") String openId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.teacherCHistory(openId);
    }

    @RequestMapping(value = "/{appid}/teacher/fix_sign", method = RequestMethod.POST)
    public ResponseModel fixSign(@PathVariable String appid,
                                 @RequestParam("openId") String openId,
                                 @RequestParam("signId") int signId,
                                 @RequestParam("studentId") String studentId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.fixSign(openId, studentId, signId);
    }

    @RequestMapping(value = "/{appid}/student/already_sign", method = RequestMethod.POST)
    public ResponseModel alreadySign(@PathVariable String appid,
                                    @RequestParam("openId") String openId,
                                     @RequestParam("signId") int signId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return atdService.alreadySign(openId, signId);
    }
}
