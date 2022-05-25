package com.dahuang.until;

import com.dahuang.model.enums.Roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/8 11:16
 */
public class Constant {

    /**
     * 保存客户端登录Id
     */
    public static final String USER_TOKEN = "token";

    /**
     * 访问权限
     */
    public static final Map<Roles, List<String>> Permission = new HashMap<>();

    /**
     * 初始化权限信息
     */
    static {
        List<String> manager = new ArrayList<>();
        manager.add("/admin/page/application");
        manager.add("/admin/application/agree");
        manager.add("/admin/application/reject");
        manager.add("/user/alter");
        manager.add("/user/add_shopcart");
        manager.add("/user/shopCart");
        manager.add("/user/deleteCart");
        manager.add("/user/clearCart");
        manager.add("/user/orders");
        manager.add("/user/receive_firm");



        List<String> merchant = new ArrayList<>();
        merchant.add("/merchant/product/add");
        merchant.add("/merchant/product/alter");
        merchant.add("/merchant/product/delete");
        merchant.add("/merchant/product/send");
        merchant.add("/merchant/products");
        merchant.add("/merchant/orders");
        merchant.add("/user/alter");
        merchant.add("/user/add_shopcart");
        merchant.add("/user/shopCart");
        merchant.add("/user/deleteCart");
        merchant.add("/user/clearCart");
        merchant.add("/user/orders");
        merchant.add("/user/receive_firm");

        List<String> customer = new ArrayList<>();
        customer.add("/user/alter");
        customer.add("/user/add_shopcart");
        customer.add("/user/shopCart");
        customer.add("/user/deleteCart");
        customer.add("/user/clearCart");
        customer.add("/user/orders");
        customer.add("/user/receive_firm");

        Permission.put(Roles.Manager,manager);
        Permission.put(Roles.Merchant,merchant);
        Permission.put(Roles.Customer,customer);

    }

}
