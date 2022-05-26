package com.tracker.nlp.interceptor;


import cn.hutool.core.util.StrUtil;
import com.tracker.nlp.annotation.Authorization;
import com.tracker.nlp.exception.AbnormalLoginException;
import com.tracker.nlp.exception.MissAuthorizationException;
import com.tracker.nlp.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: AuthorizationInterceptor.java
 * @LastModified: 2021/11/10 15:34:10
 */

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {
    public final static String HEADER_KEY_UID = "Uid";
    public final static String HEADER_KEY_AUTHORIZATION = "Authorization";

    @Resource
    private AuthorizationService mAuthorizationService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        Authorization authorization = method.getMethodAnnotation(Authorization.class);
        if (authorization != null) {
            String uid = request.getHeader(HEADER_KEY_UID);
            String token = request.getHeader(HEADER_KEY_AUTHORIZATION);
            if (StrUtil.isEmpty(token) || StrUtil.isEmpty(uid)) {
                throw new MissAuthorizationException("参数异常，鉴权失败");
            }
            if (mAuthorizationService.checkLogin(uid, token)) {
                return true;
            } else {
                throw new AbnormalLoginException("登录态异常，资源访问受限");
            }
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, @Nullable ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, @Nullable Exception ex) {
    }

}
