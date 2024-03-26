package com.liwei.rent.common.Enum;

public enum ErrorCodeEnum {
    USERNAME_OR_PASSWORD_NOT_CORRECT(1001,"用户名或密码错误"),
    USERNAME_OR_PASSWORD_IS_EMPTY(1002,"用户名或密码为空"),
    USER_LOCK(1003,"用户已锁定"),
    USER_NOT_EXIST(1004,"用户不存在"),
    USER_NO_TOKEN(1005,"用户未登录"),
    DIC_KEY_IS_NULL(2001,"字典key为空")
    ;
    private Integer code;
    private String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code(){
        return this.code;
    }

    public String msg(){
        return this.message;
    }
}
