package com.liwei.rent.common.Enum;

public enum TenantStatusEnum {
    ON_RENT("在租","0"),
    OFF_RENT("退租","1"),
    ;
    private String name;
    private String value;

    TenantStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
