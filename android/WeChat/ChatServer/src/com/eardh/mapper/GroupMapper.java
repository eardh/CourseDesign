package com.eardh.mapper;

import com.eardh.model.pojo.Group;
import com.eardh.model.pojo.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

public class GroupMapper extends BaseMapper{

    public List<User> queryMembers(String groupId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (List<User>) query(User.class, "select * from user where userId in " +
                "(select group_userId from chat_group where groupId=?)", groupId);
    }

    public Group queryGroupInfo(String groupId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<?> query = query(Group.class, "select * from group_info where groupId=?", groupId);
        return query.isEmpty() ? null : (Group) query.get(0);
    }

    public boolean addMember(String groupId, String userId) throws SQLException {
        return execute("insert into chat_group values(null,?,?)", groupId, userId);
    }

    public boolean deleteMember(String groupId, String userId) throws SQLException {
        return execute("delete from chat_group where groupId=? and userId=?");
    }
}
