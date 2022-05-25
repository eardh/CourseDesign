package com.eardh.model.pojo;

import java.util.Date;

public class Message {

    private String from;       // 消息来源
    private String to;         // 消息给谁
    private int type;          // 消息类型，自己 | 他人
    private String verify_code; // 验证信息
    private String content;     // 消息体
    private Date date;          // 日期

    public Message() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
