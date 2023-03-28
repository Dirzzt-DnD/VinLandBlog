package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constant.VinlandConstant;
import com.warzero.vinlandblog.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = REQUEST_MODEL+"/userInfo", method = RequestMethod.GET)
    public ResponseResult getUserInfo(){
       return userService.getUserInfo();
    }
}
