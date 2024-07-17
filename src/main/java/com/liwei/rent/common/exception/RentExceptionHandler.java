package com.liwei.rent.common.exception;

import com.liwei.rent.common.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RentExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleRentException(RentException e){
        return Result.error(e.getCode(),e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result ValidateException(MethodArgumentNotValidException  e){
        return Result.error(400,e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
