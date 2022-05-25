package com.eardh.wechat.model.vojo;

import java.util.HashMap;
import java.util.Map;

public class EarRequest {

    private String state;
    private String url;
    private String protocol;
    private String key;
    private Map<String, Object> body;

    public EarRequest() {
    }

    public EarRequest(Builder builder) {
        this.state = builder.state;
        this.url = builder.url;
        this.protocol = builder.protocol;
        this.key = builder.key;
        this.body = builder.body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, Object> getParams() {
        return body;
    }

    public void setParams(Map<String, Object> body) {
        this.body = body;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static class Builder {
        private String state;
        private String url;
        private String protocol;
        private String key;
        private Map<String, Object> body;

        public Builder() {
            body = new HashMap<>();
        }

        public String getState() {
            return state;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getProtocol() {
            return protocol;
        }

        public Builder setProtocol(String protocol) {
            this.protocol = protocol;
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

        public EarRequest builder() {
            return new EarRequest(this);
        }
    }
}
