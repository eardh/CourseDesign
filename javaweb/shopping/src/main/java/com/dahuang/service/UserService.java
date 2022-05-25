package com.dahuang.service;

import com.dahuang.model.response.ResponseModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author dahuang
 * @date 2021/6/8 11:13
 */
@Transactional
public interface UserService{

    /**
     * 登录
     * @author dahuang
     * @date 2021/6/9 20:08
     * @param userName
     * @param password
     * @param session
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel login(String userName, String password, HttpSession session);

    /**
     * 注销
     * @author dahuang
     * @date 2021/6/9 20:09
     * @param session
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel logout(HttpSession session);

    /**
     * 新用户注册
     * 1、普通用户不需要审核
     * 2、商家入住需要审核，管理员系统内定，无法注册
     * @author dahuang
     * @date 2021/6/10 16:29
     * @param file
     * @param request
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel register(MultipartFile file, HttpServletRequest request);

    /**
     * 用户资料修改
     * @author dahuang
     * @date 2021/6/10 16:46
     * @param file
     * @param request
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel updateUserInfo(MultipartFile file, HttpServletRequest request);

    /**
     *  用户名是否可用检查
     * @author dahuang
     * @date 2021/6/13 10:55
     * @param userName
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel checkUserName(String userName);


    /**
     * 增加商品到购物车
     * @author dahuang
     * @date 2021/6/13 15:23
     * @param request
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel addToShopCart(HttpServletRequest request);

    /**
     * 查询登录用户的购物车
     * @author dahuang
     * @date 2021/6/13 17:32
     * @param customerID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel queryUserShopCart(String customerID);

    /**
     * 删除购物车
     * @author dahuang
     * @date 2021/6/13 21:43
     * @param ID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel deleteCart(String ID);

    /**
     * 清空购物车
     * @author dahuang
     * @date 2021/6/14 11:28
     * @param request
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel  clearCart(HttpServletRequest request);

    /**
     * 分页查询订单信息
     * @author dahuang
     * @date 2021/6/14 13:42
     * @param currentPage
     * @param pageSize
     * @param userID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel queryUserOrder(int currentPage, int pageSize,String userID);

    /**
     * 客户更新订单状态
     * @author dahuang
     * @date 2021/6/14 15:44
     * @param type
     * @param orderID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel updateOrderStatus(String type,String orderID);

    /**
     * 商家查询订单分页处理
     * @author dahuang
     * @date 2021/6/15 10:36
     * @param currentPage
     * @param pageSize
     * @param merchantID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel MerchantIDToOrders(int currentPage, int pageSize,String merchantID);

    /**
     * 商家发货
     * @author dahuang
     * @date 2021/6/15 13:00
     * @param ID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel updateOrderSend(String ID);

    /**
     * 管理员查看商家入驻申请
     * @author dahuang
     * @date 2021/6/15 13:36
     * @param currentPage
     * @param pageSize
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel pageUsersByManager(int currentPage, int pageSize);

    ResponseModel agreeMerchantApplication(String ID);

    ResponseModel rejectMerchantApplication(String ID);

}
