package com.liwei.rent.common.Enum;

public enum DelFlagEnum {
    UN_DEL("未删除",0),
    DEL("已删除",1)
    ;
    private String name;
    private int value;

    DelFlagEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
