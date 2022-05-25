package com.dahuang.service;


import com.dahuang.model.json.ResponseJson;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Transactional
public interface SecurityService {

    ResponseJson login(String username, String password, HttpSession session);

    ResponseJson logout(HttpSession session);

    ResponseJson confirm_userName(String userName);

    ResponseJson register(String userName,String password) throws Exception;
}
