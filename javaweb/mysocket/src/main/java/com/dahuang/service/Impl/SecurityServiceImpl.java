package com.dahuang.service.Impl;

import com.dahuang.mapper.UserMapper;
import com.dahuang.model.json.ResponseJson;
import com.dahuang.model.pojo.User;
import com.dahuang.service.SecurityService;
import com.dahuang.utils.Constant;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;

@Service
public class SecurityServiceImpl implements SecurityService {



    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringEncryptor stringEncryptor;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);


    /**
     *  登录处理
     * @param username
     * @param password
     * @param session
     * @return
     */
    @Override
    public ResponseJson login(String username, String password, HttpSession session) {
        User User = userMapper.getByUsername(username);
        if (User == null) {
            return new ResponseJson().error("不存在该用户名");
        }
        if (!stringEncryptor.decrypt(User.getPassword()).equals(password)) {
            return new ResponseJson().error("密码不正确");
        }
        session.setAttribute(Constant.USER_TOKEN, User.getUserId());
        return new ResponseJson().success();
    }

    /**
     *  注销
     * @param session
     * @return
     */
    @Override
    public ResponseJson logout(HttpSession session) {
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        if (userId == null) {
            return new ResponseJson().error("您尚未登录，是否跳转到登录页面？");
        }
        session.removeAttribute(Constant.USER_TOKEN);
        LOGGER.info(MessageFormat.format("userId为 {0} 的用户已注销登录!", userId));
        return new ResponseJson().success();
    }

    /**
     *  用户名校验
     * @return
     */
    @Override
    public ResponseJson confirm_userName(String userName) {

        User user = userMapper.getByUsername(userName);

        if(user == null) {
            return new ResponseJson()
                    .setData("hd","yes")
                    .setData("src","img/yes.png");
        }
        return new ResponseJson()
                .setData("hd","no")
                .setData("src","img/no.png");
    }

    /**
     *  注册表单处理
     * @param userName
     * @param password
     * @return
     */
    @Override
    public ResponseJson register(String userName, String password) throws Exception {
        try {
            userMapper.registerUser(userName, stringEncryptor.encrypt(password));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception();
        }
        return new ResponseJson().success()
                .setData("success","注册成功，请牢记您地用户名："+userName);
    }
}
