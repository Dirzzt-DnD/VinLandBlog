package com.warzero.vinlandblog.service.imp;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.LoginUser;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.dto.LoginUserDTO;
import com.warzero.vinlandblog.domain.vo.BlogUserLoginVo;
import com.warzero.vinlandblog.domain.vo.UserInfoVo;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.mapper.UserMapper;
import com.warzero.vinlandblog.service.BlogLoginService;
import com.warzero.vinlandblog.utils.Assert;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import com.warzero.vinlandblog.utils.JwtUtils;
import com.warzero.vinlandblog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BlogLoginServiceImp implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(LoginUserDTO user) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Assert.notNull(user, AppHttpCodeEnum.LOGIN_ERROR);

        LoginUser loginUser =(LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token  = JwtUtils.createJWT(userId);
        redisCache.setCacheObject(SystemConstants.REDIS_USER_ID_PREFIX + userId, loginUser);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        userInfoVo.setIsAdmin(SystemConstants.ADMIN_USER.equals(loginUser.getUser().getType()));
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token,userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult loginout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        redisCache.deleteObject(userId);
        return ResponseResult.okResult();
    }
}
