package com.dahuang.controller;

import com.dahuang.model.response.ResponseModel;
import com.dahuang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dahuang
 * @date 2021/6/9 16:40
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/page/application", method = RequestMethod.POST)
    public ResponseModel confirmOrder(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize) {
        return userService.pageUsersByManager(currentPage,pageSize);
    }

    @RequestMapping(value = "/application/agree", method = RequestMethod.POST)
    public ResponseModel agreeApplication(
            @RequestParam("ID") String ID ){
        return userService.agreeMerchantApplication(ID);
    }

    @RequestMapping(value = "/application/reject", method = RequestMethod.POST)
    public ResponseModel rejectApplication(
            @RequestParam("ID") String ID) {
        return userService.rejectMerchantApplication(ID);
    }

}
