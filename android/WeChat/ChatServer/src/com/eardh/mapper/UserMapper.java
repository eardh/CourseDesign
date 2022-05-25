package com.eardh.mapper;

import com.eardh.model.pojo.Group;
import com.eardh.model.pojo.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

public class UserMapper extends BaseMapper{

    public List<User> queryFriends(String id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
       return (List<User>) query(User.class, "select * from user where userId in" +
                "(select fuserId from chat_friends where userId=? " +
                "union select userId from chat_friends where fuserId=?)", id, id);
    }

    public List<Group> queryGroups(String id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (List<Group>) query(Group.class, "select * from group_info where groupId in" +
                "(select groupId from chat_group where group_userId=?)", id);
    }

    public User queryUserInfo(String id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<?> query = query(User.class, "select * from user where userId=?", id);
        return query.isEmpty() ? null : (User) query.get(0);
    }

    public User queryById_Password(String id, String password) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<?> query = query(User.class, "select * from user where userId=? and password=?", id, password);
        return query.isEmpty() ? null : (User) query.get(0);
    }

    public String queryUserState(String id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<?> query = query(String.class, "select ip_address from online_state where userId=?", id);
        return query.isEmpty() ? null : (String) query.get(0);
    }

    public boolean updateUserState(String id, int state) throws SQLException {
        return execute("update online_state set state=? where userId=?", state, id);
    }

    public boolean addOnlineUser(String id, int state, String ip) throws SQLException {
        return execute("insert into online_state values(?,?,?)", id, state, ip);
    }

    public boolean deleteOnlineUser(String ip, String id) throws SQLException {
        return execute("delete from online_state where ip_address=? or userId=?", ip, id);
    }

    public boolean addFriend(String userId, String fuserId) throws SQLException {
        return execute("insert into chat_friends values(null,?,?,now())", userId, fuserId);
    }

    public String insertUser(String nickname, String password) throws SQLException {
        Connection connection = pool.get();
        String sql = "insert into user (nickname, password) values(?,?)";
        Savepoint savepoint = connection.setSavepoint();
        String userId = null;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, nickname);
            statement.setString(2, password);
            statement.execute();
            connection.commit();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = String.valueOf(generatedKeys.getLong(1));
            }
        } catch (SQLException throwables) {
            connection.rollback(savepoint);
        }
        pool.free(connection);
        return userId;
    }
}