package com.dahuang.model.enums;

public enum ProductTypes {

    ELECTRONIC_PRODUCT("电子产品"),
    HOUSEHOLD_APPLICATION("家用电器"),
    FRESH_FOOD("食品生鲜"),
    LITERATURE("文学书刊"),
    CLOTHING_LUGGAGE("服装箱包");

    private String type;

    ProductTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
