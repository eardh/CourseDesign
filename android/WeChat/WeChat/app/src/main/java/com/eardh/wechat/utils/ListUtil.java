package com.eardh.wechat.utils;

import com.eardh.wechat.model.pojo.ChatMessage;
import com.eardh.wechat.model.pojo.Group;
import com.eardh.wechat.model.pojo.User;

public class ListUtil {

    public static boolean checkAndReplace(ChatMessage chatMessage) {
        for (ChatMessage message : Constant.newMessage) {
            if (message.getID().equals(chatMessage.getID())) {
                if (Constant.messageAdapter == null) {
                    message.setType(1);
                }
                message.setContent(chatMessage.getContent());
                return false;
            }
        }
        return true;
    }

    public static User toPrivateChat(String id) {
        for (User friend : Constant.friends) {
            if (friend.getUserId().equals(id)) {
                return friend;
            }
        }
        return null;
    }

    public static Group toGroupChat(String id) {
        for (Group group : Constant.groups) {
            if (group.getGroupId().equals(id)) {
                return group;
            }
        }
        return null;
    }
}
