package com.huang.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Course {

    private String courseId;
    private String instructor;
    private String courseTitle;

}
