package com.eardh.chatroom.Entity;

public class MsgInfo {

    private String name; // 发送者昵称
    private String content; // 消息内容
    private int type; // 消息类型

    public MsgInfo() {
    }

    public MsgInfo(String name, String content) {
        this(name, content, 0);
    }

    public MsgInfo(String name, String content, int type) {
        this.name = name;
        this.content = content;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}

