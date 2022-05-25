package com.dahuang.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private String groupId;
    private String groupName;
    private String groupAvatarUrl;
    private List<User> members;

}
