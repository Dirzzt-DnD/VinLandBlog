package com.warzero.vinlandblog.utils;

import com.warzero.vinlandblog.domain.LoginUser;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.excepetion.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {

    public static Authentication getAuthentication(){
       return SecurityContextHolder.getContext().getAuthentication();
    }

    public static LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        Long userId = null;
        try {
            userId = getLoginUser().getUser().getId();
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        return userId;
    }



}
