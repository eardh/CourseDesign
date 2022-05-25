package com.eardh.model.vojo;

import java.util.HashMap;
import java.util.Map;

public class EarResponse {

    private int code;      // 响应码
    private String msg;    // 响应信息
    private String key;    // 密钥
    private Map<String, Object> body;  // 响应体

    public EarResponse() {
    }

    public EarResponse(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.key = builder.key;
        this.body = builder.body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EarResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", key='" + key + '\'' +
                ", body=" + body +
                '}';
    }

    public static class Builder {
        private int code;
        private String msg;
        private String key;
        private Map<String, Object> body;

        public Builder() {
            body = new HashMap<>();
        }

        public int getCode() {
            return code;
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public String getMsg() {
            return msg;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public String getKey() {
            return key;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setData(String key, Object value) {
            body.put(key, value);
            return this;
        }

        public EarResponse builder() {
            return new EarResponse(this);
        }
    }
}
