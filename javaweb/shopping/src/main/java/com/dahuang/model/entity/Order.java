package com.dahuang.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author dahuang
 * @date 2021/6/13 17:43
 */
@Data
@AllArgsConstructor
public class Order {

    private String ID;
    private String customerID;
    private String merchantID;
    private String productID;
    private String productName;
    private Integer count;
    private Double totalMoney;
    private Date date;
    private String serial_number;
    private boolean send;
    private boolean received;
    private boolean confirm;

}
