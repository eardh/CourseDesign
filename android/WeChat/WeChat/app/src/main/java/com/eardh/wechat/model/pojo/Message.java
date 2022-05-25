package com.eardh.wechat.model.pojo;

import java.util.Date;

public class Message {

    private String from;
    private String to;
    private int type;
    private String verify_code;
    private String content;
    private Date date;

    public Message() {
    }

    public Message(String from, String to, int type, String content) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.content = content;
        this.date = new Date();
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
