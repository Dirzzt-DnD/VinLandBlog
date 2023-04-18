package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.warzero.vinlandblog.domain.LoginUser;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.mapper.AccessMapper;
import com.warzero.vinlandblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccessMapper accessMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(User::getUserName,username);
        User user = userMapper.selectOne(userQuery);
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        List<String> permissions = accessMapper.selectPermissionsByUserId(user.getId());
        return new LoginUser(user, permissions);
    }
}
