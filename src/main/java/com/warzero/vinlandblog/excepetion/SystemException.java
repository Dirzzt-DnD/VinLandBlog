package com.warzero.vinlandblog.excepetion;

import com.warzero.vinlandblog.enums.AppHttpCodeEnum;

public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public SystemException(AppHttpCodeEnum appHttpCodeEnum){
        super(appHttpCodeEnum.getMsg());
        this.code = appHttpCodeEnum.getCode();
        this.msg  = appHttpCodeEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }
}
