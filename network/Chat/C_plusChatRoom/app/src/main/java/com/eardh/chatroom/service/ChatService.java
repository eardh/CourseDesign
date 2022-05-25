package com.eardh.chatroom.service;

import android.os.Handler;
import android.util.Log;

import com.eardh.chatroom.Entity.MsgInfo;
import com.eardh.chatroom.utils.MessageState;
import java.util.List;

public class ChatService {

    static {
        System.loadLibrary("chatroom");
    }

    private static String nickname;
    private final List<MsgInfo> messages;
    private Handler handler;
    private volatile boolean valid;
    private String ip;

    public ChatService(List<MsgInfo> messages, Handler handler, String ip) {
        this.messages = messages;
        this.handler = handler;
        this.ip = ip;
        valid = false;
    }

    public void start() {
        working(this.ip, ChatService.nickname);
    }

    private native void working(String ip, String nickname);

    private void callback(int code, String content) {
        switch (code) {
            case MessageState.UPDATE_UI:
                chip_message(content);
                break;
            case MessageState.SEND_SUCCESS:
                break;
            case MessageState.CONNECT_SUCCESS:
                this.valid = true;
                break;
            default:
                this.valid = false;
        }
        handler.sendEmptyMessage(code);
    }

    public native void sendMessage(String content);

    public void close() {
        valid = false;
        exit_server();
    }

    private native void exit_server();

    public void chip_message(String s) {
        Log.i("9527", s);
        if (s.contains("\r\n\r\n")) {
            int i = s.indexOf("\r\n\r\n");
            String name = s.substring(0, i);
            String content = s.substring(i + 4);
            addMessage(new MsgInfo(name, content, 1));
        } else {
            addMessage(new MsgInfo("server", s, 2));
        }
    }

    public void addMessage(MsgInfo msgInfo) {
        synchronized (messages) {
            messages.add(msgInfo);
        }
    }

    public static void setNickname(String nickname) {
        ChatService.nickname = nickname;
    }

    public boolean isValid() {
        return valid;
    }
}
