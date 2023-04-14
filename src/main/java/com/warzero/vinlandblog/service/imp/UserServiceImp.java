package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.vo.UserInfoVo;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.mapper.UserMapper;
import com.warzero.vinlandblog.service.UserService;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import com.warzero.vinlandblog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Security;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = userMapper.getAllById(userId);
        if(user != null){
            return ResponseResult.okResult(BeanCopyUtils.copyBean(user, UserInfoVo.class));
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
    }

    @Override
    public ResponseResult getAdminInfo() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getType, SystemConstants.ADMIN_USER);
        User user = getOne(wrapper,false);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(user, UserInfoVo.class));
    }
}
