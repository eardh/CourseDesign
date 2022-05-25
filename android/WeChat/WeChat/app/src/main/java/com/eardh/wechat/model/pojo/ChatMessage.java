package com.eardh.wechat.model.pojo;

import androidx.annotation.NonNull;

public class ChatMessage implements Cloneable{

    private String ID;
    private String nickname;
    private int type;
    private String content;
    private boolean personal;

    public ChatMessage() {
    }

    @Override
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        return obj;
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
