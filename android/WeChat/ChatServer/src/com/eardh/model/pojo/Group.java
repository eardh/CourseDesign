package com.eardh.model.pojo;

import java.util.List;

public class Group {

    private String groupId;        // 群组ID
    private String nickname;       // 群组名称
    private String groupAvatarUrl; // 群组头像

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