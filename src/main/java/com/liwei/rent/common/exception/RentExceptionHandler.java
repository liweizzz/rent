package com.liwei.rent.common.exception;

import com.liwei.rent.common.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RentExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleRentException(RentException e){
        return Result.error(e.getCode(),e.getMessage());
    }
}
