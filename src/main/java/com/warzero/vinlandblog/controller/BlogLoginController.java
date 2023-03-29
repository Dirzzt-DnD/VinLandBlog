package com.warzero.vinlandblog.controller;


import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.dto.LoginUserDTO;
import com.warzero.vinlandblog.service.BlogLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Tag(name = "登录处理")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @Operation(summary = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@Valid @RequestBody LoginUserDTO user){
        return blogLoginService.login(user);
    }

    @Operation(summary = "登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseResult loginOut(){
        return blogLoginService.loginout();
    }

    @Operation(summary = "注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseResult register(@Valid @RequestBody User user){
        return blogLoginService.register(user);
    }
}
