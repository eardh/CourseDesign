package com.huang.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SignInfo {

    private int signId;
    private String teacherName;
    private String courseName;
    private int signType;
    private String signCode;
    private String signLocation;
    private long startTime;
    private long signDuring;

}
