package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.dto.UserDto;
import com.warzero.vinlandblog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Tag(name = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    public static final String REQUEST_MODEL = "/user";

    @Operation(summary = "获得管理员")
    @RequestMapping(value = REQUEST_MODEL+"/userInfo", method = RequestMethod.GET)
    public ResponseResult getUserInfo(){
       return userService.getUserInfo();
    }

    @Operation(summary = "获得管理员")
    @RequestMapping(value = REQUEST_MODEL+"/adminInfo",method = RequestMethod.GET)
    public ResponseResult getAdminIfo(){
        return userService.getAdminInfo();
    }

    @Operation(summary = "更新用户信息")
    @PutMapping(REQUEST_MODEL+"/userInfo")
    public ResponseResult updateUserInfo(@Valid @RequestBody UserDto user){
        return userService.updateUserInfo(user);
    }

    @Operation(summary = "注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseResult register(@Valid @RequestBody User user){
        return userService.register(user);
    }
}
