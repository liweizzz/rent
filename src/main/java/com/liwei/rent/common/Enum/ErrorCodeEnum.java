package com.liwei.rent.common.Enum;

public enum ErrorCodeEnum {
    USERNAME_OR_PASSWORD_NOT_CORRECT(1001,"用户名或密码错误"),
    USERNAME_OR_PASSWORD_IS_EMPTY(1002,"用户名或密码为空"),
    USER_LOCK(1003,"用户已锁定"),
    USER_NOT_EXIST(1004,"用户不存在"),
    USER_INVALID_TOKEN(1005,"token失效，请重新登录"),
    USER_ID_IS_NULL(1006,"用户ID为空"),
    USER_ROLE_IS_NULL(1007,"用户无角色"),
    ROOM_NUM_IS_NULL(1008,"房间号为空"),
    USER_PERMISSION_DENIED(1009,"无此操作权限"),

    DIC_KEY_IS_NULL(2001,"字典key为空"),

    TENANT_ROOM_BIND(3001,"房间已绑定租户"),
    TENANT_IS_NOT_EXIST(3002,"租户不存在"),
    APPARTENT_IS_NULL(3003,"公寓不能为空"),
    MONTH_IS_NULL(3004,"月份不能为空"),

    RECEIPT_CUR_ELECNUM_IS_ERROR(4001,"期末电表度数错误！"),

    RECEIPT_IS_NOT_EXIST(5001,"收据不存在"),

    CREATE_REPORT_ERROR(6001,"创建报表失败"),
    MONTH_IS_ILLEGAL(6002,"日期选择不正确"),

    KEY_NOT_EXIST(7001,"秘钥不存在,登录失败"),

    SYSTEM_ERROR(8001,"系统异常")
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
