package com.dahuang.model.entity;

import com.dahuang.model.enums.ProductTypes;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Product {

    private String ID;
    private String productName;
    private String merchantID;
    private String storeID;
    private Integer stock;
    private Double price;
    private ProductTypes type;
    private Date addtime;
    private String description;
    private String image;

}
