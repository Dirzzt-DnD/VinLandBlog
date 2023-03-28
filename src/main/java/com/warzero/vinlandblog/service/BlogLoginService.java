package com.warzero.vinlandblog.service;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.dto.LoginUserDTO;

public interface BlogLoginService{
    ResponseResult login(LoginUserDTO user);

    ResponseResult loginout();

    ResponseResult register(User user);
}
