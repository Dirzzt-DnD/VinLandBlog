package com.warzero.vinlandblog.filter;

import com.alibaba.fastjson.JSON;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.LoginUser;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.excepetion.SystemException;
import com.warzero.vinlandblog.utils.JwtUtils;
import com.warzero.vinlandblog.utils.RedisCache;
import com.warzero.vinlandblog.utils.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            log.info("不需要拦截");
            filterChain.doFilter(request,response);
            return;
        }

        String userId;

        try {
            Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getSubject();
        }catch (Exception e){
            resolveException(request, response);
            return;
        }

        LoginUser loginUser = redisCache.getCacheObject(SystemConstants.REDIS_USER_ID_PREFIX + userId);
        if (loginUser == null){
            resolveException(request, response);
            return;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
    private void resolveException(HttpServletRequest request, HttpServletResponse response){
        exceptionResolver.resolveException(request, response, null, new SystemException(AppHttpCodeEnum.NEED_LOGIN));
    }

}
