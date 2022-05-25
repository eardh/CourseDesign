package com.dahuang.model.entity;

import com.dahuang.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

    private String ID;
    private String userName;
    private String password;
    private Date registerTime;
    private String email;
    private String  phone;
    private String avatarImage;
    private boolean status;
    private Roles role;

}
