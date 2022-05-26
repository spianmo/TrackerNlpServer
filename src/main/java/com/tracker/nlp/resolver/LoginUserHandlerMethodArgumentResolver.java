package com.tracker.nlp.resolver;


import com.tracker.nlp.annotation.LoginUser;
import com.tracker.nlp.entity.UserAuthorization;
import com.tracker.nlp.interceptor.AuthorizationInterceptor;
import com.tracker.nlp.service.AuthorizationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: LoginUserHandlerMethodArgumentResolver.java
 * @LastModified: 2022/01/15 16:06:15
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserAuthorization.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        String uid = request.getHeader(AuthorizationInterceptor.HEADER_KEY_UID);
        if (uid == null) {
            return null;
        }
        return authorizationService.getUserAuthorization(uid);
    }
}
