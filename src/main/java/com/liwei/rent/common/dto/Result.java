package com.liwei.rent.common.dto;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> ok(){
        Result<T> result = new Result<>(200,"成功");
        return result;
    }

    public static Result error(Integer code,String message){
        return new Result(code,message);
    }

    public static <T> Result<T> build(T data){
        Result<T> result = new Result<>(200,"成功",data);
        return result;
    }
}
