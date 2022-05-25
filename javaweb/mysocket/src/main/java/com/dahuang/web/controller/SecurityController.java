package com.dahuang.web.controller;

import com.dahuang.model.json.ResponseJson;
import com.dahuang.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;

@Controller
public class SecurityController {

    @Autowired
    SecurityService securityService;

    /**
     *  登录
     * @return
     */
    @RequestMapping(value = {"login", "/"}, method = RequestMethod.GET)
    public String toLogin() {
        return "/views/login";
    }

    /**
     *  登录验证
     * @param session
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(HttpSession session,
                              @RequestParam String username,
                              @RequestParam String password) {
        return securityService.login(username, password, session);
    }

    /**
     *  跳转注册页面
     * @return
     */
    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public String toRegister() {
        return "/views/register";
    }


    @RequestMapping(value = "confirm_userName", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson confirm_userName(
            @RequestParam String username) {
        return securityService.confirm_userName(username);
    }

    @RequestMapping(value = "Register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson Register(
            @RequestParam String username,
            @RequestParam String password) throws Exception {

        return securityService.register(username, password);

    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson logout(HttpSession session) {
        return securityService.logout(session);
    }
}
