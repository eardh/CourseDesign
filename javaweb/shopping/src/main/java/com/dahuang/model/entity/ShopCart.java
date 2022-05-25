package com.dahuang.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopCart {

    private String ID;
    private String customerID;
    private String merchantID;
    private String productID;
    private Integer productCount;

}
