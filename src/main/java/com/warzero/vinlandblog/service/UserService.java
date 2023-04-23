package com.warzero.vinlandblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.dto.UserDto;

public interface UserService extends IService<User> {
    ResponseResult getUserInfo();

    ResponseResult getAdminInfo();

    ResponseResult register(User user);

    ResponseResult updateUserInfo(UserDto user);
}