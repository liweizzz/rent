package com.liwei.rent.common.Enum;

public enum CommonStatusEnum {
    ON("开启",0),
    OFF("关闭",1)
    ;

    private String name;
    private int value;

    CommonStatusEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int value(){
        return this.value;
    }
}
