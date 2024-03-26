package com.liwei.rent.common.Enum;

public enum UserStatusEnum {
    NORMAL("正常","0"),
    LOCK("锁定","1"),
    ;
    private String name;
    private String value;

    UserStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
