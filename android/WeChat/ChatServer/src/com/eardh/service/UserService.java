package com.eardh.service;

import com.eardh.mapper.UserMapper;
import com.eardh.model.pojo.ChatMessage;
import com.eardh.model.pojo.Group;
import com.eardh.model.pojo.Message;
import com.eardh.model.pojo.User;
import com.eardh.model.vojo.EarRequest;
import com.eardh.model.vojo.EarResponse;
import com.eardh.model.vojo.OnlineState;
import com.eardh.utils.Constant;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserMapper userMapper = new UserMapper();

    public EarResponse login(String id, String password, String ip) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User user = userMapper.queryById_Password(id, password);
        if (user == null) {
            return new EarResponse.Builder()
                    .setCode(Constant.LOGIN_FAIL)
                    .setMsg("用户名或密码错误")
                    .builder();
        }
        userMapper.addOnlineUser(id, OnlineState.ONLINE.getCode(), ip);
        return new EarResponse.Builder()
                .setCode(Constant.LOGIN_SUCCESS)
                .setData("online_user", user)
                .setMsg("登录成功")
                .builder();
    }

    public EarResponse logout(String ip, String id) throws SQLException {
        EarResponse.Builder builder = new EarResponse.Builder()
                .setCode(Constant.LOGOUT_FAIL)
                .setMsg("失败");
        if (!userMapper.deleteOnlineUser(ip, id)) {
            return builder.builder();
        }
        return builder.setCode(Constant.LOGOUT_SUCCESS)
                .setMsg("注销成功")
                .builder();
    }

    public EarResponse getFriends(String id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<User> friends = userMapper.queryFriends(id);
        return new EarResponse.Builder()
                .setCode(Constant.REQUEST_FRIENDS)
                .setMsg("获取好友成功")
                .setData("friends", friends)
                .builder();
    }

    public EarResponse getGroups(String id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Group> groups = userMapper.queryGroups(id);
        return new EarResponse.Builder()
                .setCode(Constant.REQUEST_GROUPS)
                .setMsg("获取群组成功")
                .setData("groups", groups)
                .builder();
    }

    public EarResponse chatFriend(Message message) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String ip = userMapper.queryUserState(message.getTo());
        if (ip == null) {
            return new EarResponse.Builder()
                    .setCode(Constant.SEND_FAIL)
                    .setMsg("好友不在线")
                    .builder();
        }
        User user = userMapper.queryUserInfo(message.getFrom());
        if (user == null) {
            return new EarResponse.Builder()
                    .setCode(Constant.SEND_FAIL)
                    .setMsg("发送失败")
                    .builder();
        }
        ChatMessage chatMessage = new ChatMessage(user.getUserId(), user.getNickname(), 1, message.getContent(), true);
        return new EarResponse.Builder()
                .setCode(Constant.FRIENDS_MESSAGE)
                .setMsg("收到消息")
                .setData("ip", ip)
                .setData("chat_message", chatMessage)
                .builder();
    }

    public EarResponse addFriend(String userId, String fuserId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (userId.equals(fuserId)) {
            return new EarResponse.Builder()
                    .setCode(Constant.REQUEST_FAIL)
                    .setMsg("无法添加自己")
                    .builder();
        }
        User user = userMapper.queryUserInfo(fuserId);
        if (user == null) {
            return new EarResponse.Builder()
                    .setCode(Constant.REQUEST_FAIL)
                    .setMsg("查无此人")
                    .builder();
        }
        List<User> users = userMapper.queryFriends(userId);
        for (User user1 : users) {
            if (user1.getUserId().equals(fuserId)) {
                return new EarResponse.Builder()
                        .setCode(Constant.REQUEST_FAIL)
                        .setMsg("已是好友")
                        .builder();
            }
        }
        if (!userMapper.addFriend(userId, fuserId)) {
            return new EarResponse.Builder()
                    .setCode(Constant.REQUEST_FAIL)
                    .setMsg("添加失败")
                    .builder();
        }
        return new EarResponse.Builder()
                .setCode(Constant.ADD_FRIEND)
                .setMsg("添加成功")
                .setData("user", user)
                .builder();
    }

    public EarResponse registerUser(String nickname, String password) throws SQLException {
        String userId = userMapper.insertUser(nickname, password);
        if (userId == null) {
            return new EarResponse.Builder()
                    .setCode(Constant.REQUEST_FAIL)
                    .setMsg("注册失败")
                    .builder();
        }
        return new EarResponse.Builder()
                .setCode(Constant.REGISTER_SUCCESS)
                .setMsg("注册成功")
                .setData("userId", userId)
                .builder();
    }
}
