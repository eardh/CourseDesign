package com.dahuang.service;


import com.dahuang.model.json.ResponseJson;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    ResponseJson getByUserId(String userId);

    ResponseJson existFriendsByUserId(String userId,String fuserId);

    ResponseJson addfIdById(String userId,String fId,String tp) throws Exception;

    ResponseJson updateAvatar(String userId,String fileUrl) throws Exception;

    ResponseJson deleteById(String userId,String fId,String tp) throws Exception;
}
