package com.dahuang.mapper;


import com.dahuang.model.pojo.Group;
import com.dahuang.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GroupMapper {

    Group getByGroupId(@Param("groupId") String groupId);

    List<User> getUsersByGroupId(@Param("groupId") String groupId);

    int addMemberById(@Param("userId") String userId,@Param("groupId") String groupId);

    int deleteMemberById(@Param("userId") String userId,@Param("groupId") String groupId);
}
