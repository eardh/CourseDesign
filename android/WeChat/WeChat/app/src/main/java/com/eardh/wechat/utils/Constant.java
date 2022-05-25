package com.eardh.wechat.utils;

import android.os.Handler;

import com.eardh.wechat.adapter.FriendRecyclerViewAdapter;
import com.eardh.wechat.adapter.GroupRecyclerViewAdapter;
import com.eardh.wechat.adapter.MessageAdapter;
import com.eardh.wechat.adapter.NewMessageAdapter;
import com.eardh.wechat.model.pojo.ChatMessage;
import com.eardh.wechat.model.pojo.Group;
import com.eardh.wechat.model.pojo.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Constant {

    public static final int CONNECTION_SUCCESS = 100;
    public static final int CONNECTION_FAIL = 101;
    public static final int CONNECTION_EXCEPTION = 102;
    public static final int SEND_SUCCESS = 103;
    public static final int SEND_FAIL = 104;
    public static final int NO_CONNECTION = 105;
    public static final int LOGIN_SUCCESS = 106;
    public static final int LOGIN_FAIL = 107;
    public static final int REQUEST_FAIL = 108;
    public static final int REQUEST_FRIENDS = 109;
    public static final int REQUEST_GROUPS = 110;
    public static final int GO_CHAT_FRIEND_FRAME = 111;
    public static final int GO_CHAT_GROUP_FRAME = 112;
    public static final int FRIENDS_MESSAGE = 113;
    public static final int GROUP_MESSAGE = 114;
    public static final int ADD_FRIEND = 115;
    public static final int ADD_GROUP = 116;
    public static final int LOGOUT_SUCCESS = 117;
    public static final int LOGOUT_FAIL = 118;
    public static final int REGISTER_SUCCESS = 119;

    public static Handler current;
    public static User online_USER;
    public static List<User> friends = new CopyOnWriteArrayList<>();
    public static List<Group> groups = new CopyOnWriteArrayList<>();

    public static FriendRecyclerViewAdapter friendAdapter;
    public static GroupRecyclerViewAdapter groupAdapter;
    public static NewMessageAdapter newMessageAdapter;
    public static MessageAdapter messageAdapter;

    public static List<ChatMessage> newMessage = new CopyOnWriteArrayList<>();

    public static Map<String, List<ChatMessage>> global_map = new ConcurrentHashMap<>();

    public static void clear() {
        online_USER = null;
        friends.clear();
        friendAdapter = null;
        newMessage.clear();
        newMessageAdapter = null;
        messageAdapter = null;
        global_map.clear();
        groups.clear();
        groupAdapter = null;
    }
}
