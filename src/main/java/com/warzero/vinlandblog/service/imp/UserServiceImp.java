package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.dto.UserDto;
import com.warzero.vinlandblog.domain.vo.UserInfoVo;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.excepetion.SystemException;
import com.warzero.vinlandblog.mapper.UserMapper;
import com.warzero.vinlandblog.service.UserService;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import com.warzero.vinlandblog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

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

    @Override
    public ResponseResult register(User user) {
        LambdaQueryWrapper<User> sameQuery = new LambdaQueryWrapper<>();
        sameQuery.eq(User::getUserName,user.getUserName());
        User sameUser = userMapper.getByUserName(user.getUserName());
        if(sameUser != null){
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateUserInfo(UserDto user) {
        // 查询邮箱是否已经存在
        LambdaQueryWrapper<User> sameEmailWrapper = new LambdaQueryWrapper<>();
        sameEmailWrapper.eq(User::getEmail, user.getEmail());
        User sameEmailUser = getOne(sameEmailWrapper);
        if (sameEmailUser != null && !user.getId().equals(sameEmailUser.getId())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        updateById(BeanCopyUtils.copyBean(user, User.class));
        return ResponseResult.okResult();
    }
}
