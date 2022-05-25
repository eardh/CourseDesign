package com.eardh.wechat.model.vojo;

public enum OnlineState {

    ONLINE(1),
    OFFLINE(2),
    BUSY(3),
    IDLE(4);

    private int code;

    public int getCode() {
        return code;
    }

    OnlineState(int code) {
        this.code = code;
    }
}
