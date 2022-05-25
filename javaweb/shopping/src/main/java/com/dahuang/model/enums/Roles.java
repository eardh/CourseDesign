package com.dahuang.model.enums;

public enum Roles {

    Manager("管理员"),
    Merchant("商家"),
    Customer("客户");

    private String type;

    Roles(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
