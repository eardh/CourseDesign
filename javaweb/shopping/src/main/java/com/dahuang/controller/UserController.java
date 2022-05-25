package com.dahuang.controller;

import com.alibaba.fastjson.JSON;
import com.dahuang.model.enums.ResponseCode;
import com.dahuang.model.response.ResponseModel;
import com.dahuang.service.UserService;
import com.dahuang.until.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/8 11:28
 */
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseModel login(HttpSession session,
                                @RequestParam("userName") String username,
                                @RequestParam("password") String password) {
        logger.info(session.getId());
        return userService.login(username, password, session);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseModel logout(HttpSession session) {
        return userService.logout(session);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseModel register(
            @RequestParam(value = "avatarImage",required = false) MultipartFile file,
                                         HttpServletRequest request) {
        return userService.register(file,request);
    }

    @RequestMapping(value = "/user/alter", method = RequestMethod.POST)
    public ResponseModel UpdateUserInfo(
            @RequestParam(value = "avatarImage",required = false) MultipartFile file,
            HttpServletRequest request) {
        return userService.updateUserInfo(file,request);
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ResponseModel CheckoutUserName(
            @RequestParam(value = "userName") String userName) {
        return userService.checkUserName(userName);
    }

    @RequestMapping(value = "/check_login", method = RequestMethod.POST)
    public ResponseModel CheckUserLogin(
            @RequestParam(value = "userID") String userID,
            HttpSession session) {
        logger.info(session.getId());
        Map map = (Map) session.getAttribute(Constant.USER_TOKEN);
        if ( map !=null && map.get("userID").equals(userID)) {
            return ResponseModel.success(JSON.toJSON(map));
        }
        return ResponseModel.fail(ResponseCode.FAILURE);
    }


    @RequestMapping(value = "/user/add_shopcart", method = RequestMethod.POST)
    public ResponseModel addToShopCart(HttpServletRequest request) {
        return userService.addToShopCart(request);
    }

    @RequestMapping(value = "/user/shopCart", method = RequestMethod.POST)
    public ResponseModel lookShopCart(@RequestParam("userID") String userID) {
        return userService.queryUserShopCart(userID);
    }

    @RequestMapping(value = "/user/deleteCart", method = RequestMethod.POST)
    public ResponseModel deleteCart(@RequestParam("cartID") String cartID) {
        return userService.deleteCart(cartID);
    }

    @RequestMapping(value = "/user/clearCart", method = RequestMethod.POST)
    public ResponseModel clearCart(HttpServletRequest request) {
        return userService.clearCart(request);
    }

    @RequestMapping(value = "/user/orders", method = RequestMethod.POST)
    public ResponseModel queryUserOrder(
            @RequestParam("userID") String userID,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize) {
        return userService.queryUserOrder(currentPage,pageSize,userID);
    }

    @RequestMapping(value = "/user/receive_firm", method = RequestMethod.POST)
    public ResponseModel confirmOrder(
            @RequestParam("orderID") String orderID,
            @RequestParam("type") String type) {
        return userService.updateOrderStatus(type,orderID);
    }
}
