package com.eardh.wechat.handler;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eardh.wechat.MainActivity;
import com.eardh.wechat.model.pojo.ChatMessage;
import com.eardh.wechat.model.pojo.Group;
import com.eardh.wechat.model.pojo.User;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.model.vojo.EarResponse;
import com.eardh.wechat.page.LoginActivity;
import com.eardh.wechat.page.RegisterActivity;
import com.eardh.wechat.utils.Constant;
import com.eardh.wechat.utils.ListUtil;
import com.eardh.wechat.utils.LoadingDialog;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotifyCallback implements Handler.Callback{

    private Context context;
    private LoadingDialog loadingDialog;

    public NotifyCallback(Context context, LoadingDialog loadingDialog) {
        this.context = context;
        this.loadingDialog = loadingDialog;
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        EarResponse response = (EarResponse) msg.obj;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.hide();
        }
        switch (msg.what) {
            case Constant.CONNECTION_SUCCESS:
                break;
            case Constant.CONNECTION_FAIL:
                break;
            case Constant.CONNECTION_EXCEPTION:
                break;
            case Constant.SEND_SUCCESS:
                break;
            case Constant.SEND_FAIL:
                break;
            case Constant.NO_CONNECTION:
                break;
            case Constant.LOGIN_SUCCESS:
                Constant.online_USER = JSONObject.parseObject(String.valueOf(response.getBody().get("online_user")), User.class);
                startPage(MainActivity.class);
                break;
            case Constant.LOGIN_FAIL:
                break;
            case Constant.REQUEST_FAIL:
                break;
            case Constant.REQUEST_FRIENDS:
                List<User> friends = JSON.parseArray(JSONObject.toJSONString(response.getBody().get("friends")), User.class);
                Constant.friends.addAll(friends);
                break;
            case Constant.REQUEST_GROUPS:
                List<Group> groups = JSON.parseArray(JSONObject.toJSONString(response.getBody().get("groups")), Group.class);
                Constant.groups.addAll(groups);
                break;
            case Constant.FRIENDS_MESSAGE:
                newMessage(response, true);
                break;
            case Constant.GROUP_MESSAGE:
                newMessage(response, false);
                break;
            case Constant.ADD_FRIEND:
                User user = JSONObject.parseObject(String.valueOf(response.getBody().get("user")), User.class);
                Constant.friends.add(user);
                if (Constant.friendAdapter != null) {
                    Constant.friendAdapter.notifyDataSetChanged();
                }
                break;
            case Constant.ADD_GROUP:
                Group group = JSONObject.parseObject(String.valueOf(response.getBody().get("group")), Group.class);
                Constant.groups.add(group);
                if (Constant.groupAdapter != null) {
                    Constant.groupAdapter.notifyDataSetChanged();
                }
                break;
            case Constant.LOGOUT_SUCCESS:
                startPage(LoginActivity.class);
                break;
            case Constant.REGISTER_SUCCESS:
                String userId = response.getBody().get("userId").toString();
                new AlertDialog.Builder(context)
                        .setMessage("您申请的EarID(请牢记) : \r\n\r\n" + userId)
                        .setCancelable(false)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startPage(LoginActivity.class);
                            }
                        })
                        .show();
                break;
        }
        Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void newMessage(EarResponse response, boolean is_private) {
        ChatMessage chatMessage = JSONObject.parseObject(String.valueOf(response.getBody().get("chat_message")), ChatMessage.class);
        if (!Constant.global_map.containsKey(chatMessage.getID())) {
            Constant.global_map.put(chatMessage.getID(), new CopyOnWriteArrayList<>());
        }
        List<ChatMessage> messages = Constant.global_map.get(chatMessage.getID());
        ChatMessage clone = (ChatMessage) chatMessage.clone();
        if (!is_private) {
            clone.setNickname(String.valueOf(response.getBody().get("group_nickname")));
        }
        messages.add(chatMessage);
        Constant.global_map.put(chatMessage.getID(), messages);
        if (Constant.messageAdapter != null) {
            if (ListUtil.checkAndReplace(chatMessage)) {
                clone.setType(0);
                Constant.newMessage.add(clone);
            }
            Constant.messageAdapter.notifyDataSetChanged();
        } else {
            if (ListUtil.checkAndReplace(chatMessage)) {
                Constant.newMessage.add(clone);
            }
        }
        if (Constant.newMessageAdapter != null) {
            Constant.newMessageAdapter.notifyDataSetChanged();
        }
    }

    public void startPage(Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
