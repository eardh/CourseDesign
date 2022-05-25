package com.dahuang;

import com.alibaba.fastjson.JSON;
import com.dahuang.mapper.*;
import com.dahuang.model.entity.Order;
import com.dahuang.model.entity.Product;
import com.dahuang.model.entity.User;
import com.dahuang.model.enums.ProductTypes;
import com.dahuang.model.enums.Roles;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.dahuang.model.enums.Roles.Manager;

@SpringBootTest
class ShoppingApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StringEncryptor encryptor;

    @Test
    void contextLoads() {

//        User customer = new User("", "userName1", "password", new Date(), null, "12432", "dfsdfsd", Roles.Manager);
//        System.out.println(JSON.toJSON(customer));
//        userMapper.insertModel(customer);
//        User customer1 = new User("7", "userName", "password", new Date(), null, "12432", "dfsdfsd", Roles.Merchant);
//        userMapper.updateModel(customer1);
//        userMapper.deleteByID("7");

//        Map<String,Object> map = new HashMap<>();
//
//        map.put("startIndex",2);
//        map.put("pageSize",3);
//        List<Product> products = productMapper.pageQueryByType(map);
//        for (Product product : products) {
//            System.out.println(product);
//        }

//        System.out.println(encryptor.encrypt("123"));
//        System.out.println(encryptor.encrypt("123"));

    }
}
