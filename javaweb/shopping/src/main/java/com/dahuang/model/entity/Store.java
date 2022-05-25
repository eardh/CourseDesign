package com.dahuang.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Store {

    private String ID;
    private String merchantID;
    private String storeName;
    private Date registerTime;
    private String homeImage;

}
