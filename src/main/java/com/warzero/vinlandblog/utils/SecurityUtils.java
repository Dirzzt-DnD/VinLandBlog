package com.warzero.vinlandblog.utils;

import com.warzero.vinlandblog.domain.LoginUser;
import com.warzero.vinlandblog.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {

    public static Authentication getAuthentication(){
       return SecurityContextHolder.getContext().getAuthentication();
    }

    public static LoginUser getLoginUser(){
        String check = (String)getAuthentication().getPrincipal();
        if(check.equals("anonymousUser")){
            User user = new User();
            user.setId(Long.getLong("1"));
            LoginUser loginUser = new LoginUser(user);
        }
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }

}
