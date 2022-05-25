package com.eardh.model.pojo;

import java.util.Date;

public class ChatMessage {

    private String ID;        // 发送者的ID
    private String nickname;  // 发送者昵称
    private int type;         // 消息类型
    private String content;   // 消息体
    private boolean personal; // 是否私聊  私聊为 true

    public ChatMessage() {
    }

    public ChatMessage(String ID, String nickname, int type, String content, boolean personal) {
        this.ID = ID;
        this.nickname = nickname;
        this.type = type;
        this.content = content;
        this.personal = personal;
    }

    public boolean isPersonal() {
        return personal;
    }

    public void setPersonal(boolean personal) {
        this.personal = personal;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
