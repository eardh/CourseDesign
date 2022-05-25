package com.dahuang.mapper;


import com.dahuang.model.pojo.Group;
import com.dahuang.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserMapper {

    User getByUsername(@Param("username") String username);

    User getByUserId(@Param("userId") String userId);

    List<User> getFriendsByUserId(@Param("userId") String userId);

    List<String> getGroupIdsByUserId(@Param("userId") String userId);

    int addFriendById(@Param("userId") String userId,@Param("fuserId") String fuserId);

    int updateAvatarById(@Param("userId") String userId,@Param("avatarUrl") String avatarUrl);

    int deleteFriendById(@Param("userId") String userId,@Param("fuserId") String fuserId);

    int registerUser(@Param("username") String username,@Param("password") String password);
}
