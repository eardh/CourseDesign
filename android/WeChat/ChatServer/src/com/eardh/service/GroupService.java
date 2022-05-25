package com.eardh.service;

import com.eardh.mapper.GroupMapper;
import com.eardh.mapper.UserMapper;
import com.eardh.model.pojo.ChatMessage;
import com.eardh.model.pojo.Group;
import com.eardh.model.pojo.Message;
import com.eardh.model.pojo.User;
import com.eardh.model.vojo.EarResponse;
import com.eardh.utils.Constant;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupService {

    private GroupMapper groupMapper = new GroupMapper();
    private UserMapper userMapper = new UserMapper();

    public EarResponse chatGroup(Message message) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User from = userMapper.queryUserInfo(message.getFrom());
        List<User> users = groupMapper.queryMembers(message.getTo());
        List<String> members_addr = new ArrayList<>();
        for (User user : users) {
            String s = userMapper.queryUserState(user.getUserId());
            if (s != null) {
                members_addr.add(s);
            }
        }
        Group group = groupMapper.queryGroupInfo(message.getTo());
        if (group == null) {
            return new EarResponse.Builder()
                    .setCode(Constant.SEND_FAIL)
                    .setMsg("发送失败")
                    .builder();
        }
        ChatMessage chatMessage = new ChatMessage(message.getTo(), from.getNickname(), 1, message.getContent(), false);
        return new EarResponse.Builder()
                .setCode(Constant.GROUP_MESSAGE)
                .setMsg("收到消息")
                .setData("group_nickname", group.getNickname())
                .setData("ips", members_addr)
                .setData("chat_message", chatMessage)
                .builder();
    }

    public EarResponse addMember(String userId, String groupId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Group group = groupMapper.queryGroupInfo(groupId);
        if (group == null) {
            return new EarResponse.Builder()
                    .setCode(Constant.REQUEST_FAIL)
                    .setMsg("查无此群")
                    .builder();
        }
        List<Group> groups = userMapper.queryGroups(userId);
        for (Group group1 : groups) {
            if (group1.getGroupId().equals(groupId)) {
                return new EarResponse.Builder()
                        .setCode(Constant.REQUEST_FAIL)
                        .setMsg("已是群成员")
                        .builder();
            }
        }
        if (!groupMapper.addMember(groupId, userId)) {
            return new EarResponse.Builder()
                    .setCode(Constant.REQUEST_FAIL)
                    .setMsg("加群失败")
                    .builder();
        }
        return new EarResponse.Builder()
                .setCode(Constant.ADD_GROUP)
                .setMsg("加群成功")
                .setData("group", group)
                .builder();
    }
}
