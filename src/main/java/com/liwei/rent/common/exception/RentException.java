package com.liwei.rent.common.exception;

import com.liwei.rent.common.Enum.ErrorCodeEnum;
import lombok.Data;

@Data
public class RentException extends RuntimeException{
    private Integer code;
    private String message;

    public RentException(ErrorCodeEnum codeEnum){
        this.code = codeEnum.code();
        this.message = codeEnum.msg();
    }
}
