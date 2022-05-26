package com.tracker.nlp.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.tracker.nlp.annotation.NoRepeatSubmit;
import com.tracker.nlp.config.RedisUtil;
import com.tracker.nlp.constant.RedisKeyConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * Copyright (c) 2022
 * 不允许重复提交
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: NoRepeatSubmitInterceptor.java
 * @LastModified: 2022/01/15 17:42:15
 */

@Component
@Order
@Slf4j
@AllArgsConstructor
public class NoRepeatSubmitInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtils;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().equals(HandlerMethod.class)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean repeatSubmit = handlerMethod.getMethod().isAnnotationPresent(NoRepeatSubmit.class);
        if (repeatSubmit) {
            String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            String md5 = SecureUtil.md5(body);
            String requestURI = request.getRequestURI();
            String key = StrUtil.format(RedisKeyConstants.REPEAT_CODE, requestURI, md5);

            synchronized (key) {
                if (redisUtils.exists(key)) {
                    log.info("无效重复提交key:{} body:{}", key, body);
                    return false;
                } else {
                    log.info("key:{} body:{}", key, body);
                    redisUtils.set(key, "", 2L, TimeUnit.SECONDS);
                }
            }
        }
        return true;
    }

}
