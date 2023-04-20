package com.warzero.vinlandblog.handler;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.excepetion.SystemException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({SystemException.class})
    public ResponseResult systemExceptionHandler(SystemException e){
        log.error("发生异常",e.getMsg());
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }



}
