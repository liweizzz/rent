package com.liwei.rent.common.Enum;

import com.liwei.rent.entity.Receipt;
import com.liwei.rent.entity.Tenant;

public enum ErrorCodeEnum {
    USERNAME_OR_PASSWORD_NOT_CORRECT(1001,"用户名或密码错误"),
    USERNAME_OR_PASSWORD_IS_EMPTY(1002,"用户名或密码为空"),
    USER_LOCK(1003,"用户已锁定"),
    USER_NOT_EXIST(1004,"用户不存在"),
    USER_NO_TOKEN(1005,"用户未登录"),
    USER_ID_IS_NULL(1006,"用户ID为空"),
    ROOM_NUM_IS_NULL(1007,"房间号为空"),

    DIC_KEY_IS_NULL(2001,"字典key为空"),

    TENANT_ROOM_BIND(3001,"房间已绑定租户"),
    TENANT_IS_NOT_EXIST(3002,"租户不存在"),

    RECEIPT_CUR_ELECNUM_ISNULL(4001,"期末电表度数为空"),

    RECEIPT_IS_NOT_EXIST(5001,"收据不存在")
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
