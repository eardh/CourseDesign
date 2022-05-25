package com.eardh.wechat.model.pojo;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {

    private String groupId;
    private String nickname;
    private String groupAvatarUrl;

    public Group() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGroupAvatarUrl() {
        return groupAvatarUrl;
    }

    public void setGroupAvatarUrl(String groupAvatarUrl) {
        this.groupAvatarUrl = groupAvatarUrl;
    }

}