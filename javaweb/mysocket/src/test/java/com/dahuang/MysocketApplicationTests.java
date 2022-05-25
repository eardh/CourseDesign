package com.dahuang;

import com.dahuang.mapper.GroupMapper;
import com.dahuang.mapper.UserMapper;
import com.dahuang.service.UserService;
import com.dahuang.utils.Constant;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class MysocketApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    UserService userService;

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {

    }

}
