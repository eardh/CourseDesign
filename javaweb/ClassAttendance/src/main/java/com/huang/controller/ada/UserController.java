package com.huang.controller.ada;

import com.huang.model.response.ResponseModel;
import com.huang.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private WxMpService wxService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{appid}/auth2/login", method = RequestMethod.POST)
    public ResponseModel auth2Login(@PathVariable String appid, @RequestParam("code") String code) throws WxErrorException, IOException {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return userService.auth2Login(code);
    }

    @RequestMapping(value = "/{appid}/real_name/certification", method = RequestMethod.POST)
    public ResponseModel verified(@PathVariable String appid,
                                  @RequestParam("openId") String openId,
                                  @RequestParam("userId") String userId,
                                  @RequestParam("userName") String username,
                                  @RequestParam("identity") int identity) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return userService.verified(openId, userId, username, identity);
    }

    @RequestMapping(value = "/{appid}/private/info", method = RequestMethod.POST)
    public ResponseModel getUserInfo(@PathVariable String appid,
                                     @RequestParam("openId") String openId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return userService.getUserInfo(openId);
    }

    @RequestMapping(value = "/{appid}/teacher/courses", method = RequestMethod.POST)
    public ResponseModel teacherCourses(@PathVariable String appid,
                                        @RequestParam("openId") String openId) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return userService.teacherCourses(openId);
    }
}
