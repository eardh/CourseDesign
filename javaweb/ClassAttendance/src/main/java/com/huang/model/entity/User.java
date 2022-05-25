package com.huang.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

    private String openId;
    private String userId;
    private String userName;
    private String wxNickname;
    private int sex;
    private int identity;
    private String avatarImg;
    private boolean isSign = false;

}
