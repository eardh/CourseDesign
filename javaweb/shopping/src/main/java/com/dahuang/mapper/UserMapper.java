package com.dahuang.mapper;

import com.dahuang.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author dahuang
 * @date 2021/6/8 10:39
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{

    List<User> queryApplicationUsersToPage(Map<String,Object> map);

}
