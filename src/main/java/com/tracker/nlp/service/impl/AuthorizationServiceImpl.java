package com.tracker.nlp.service.impl;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.tracker.nlp.config.RedisUtil;
import com.tracker.nlp.constant.RedisKeyConstants;
import com.tracker.nlp.entity.UserAuthorization;
import com.tracker.nlp.exception.AbnormalLoginException;
import com.tracker.nlp.exception.InsufficAuthException;
import com.tracker.nlp.service.AuthorizationService;
import com.tracker.nlp.service.UserService;
import com.tracker.nlp.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: AuthorizationServiceImpl.java
 * @LastModified: 2022/01/24 15:44:24
 */

@Service
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    RedisUtil redis;
    @Autowired
    @Lazy
    UserService userService;

    @Override
    public boolean checkLogin(String uid, String token) {
        UserAuthorization authorization = getUserAuthorization(uid);
        if (authorization == null) {
            return false;
        }
        return authorization.getToken().equals(token);
    }

    @Override
    public String generateToken(String uid) {
        String token = RandomUtil.createRandomCharData(24).toUpperCase();
        UserAuthorization userAuthorization = UserAuthorization.builder()
                .token(token)
                .uid(uid)
                .userAccount(userService.getUserAccountById(uid))
                .build();
        boolean result = setUserAuthorization(uid, userAuthorization);
        log.debug(result ? "generateToken Success" : "generateToken Faild");
        return token;
    }

    @Override
    public void updateUserAuthorization(String uid, boolean force) {
        UserAuthorization userAuthorization = getUserAuthorization(uid);
        if (userAuthorization == null) return;
        String token = RandomUtil.createRandomCharData(24).toUpperCase();
        if (force) userAuthorization.setToken(token);
        userAuthorization.setUserAccount(userService.getUserAccountById(uid));
        setUserAuthorization(uid, userAuthorization);
    }

    @Override
    public void kickUserLogin(String uid) {
        updateUserAuthorization(uid, true);
    }

    private boolean setUserAuthorization(String uid, UserAuthorization userAuthorization) {
        return redis.set(StrUtil.format(RedisKeyConstants.USER_TOKEN, uid), userAuthorization);
    }

    @Override
    public UserAuthorization getUserAuthorization(String uid) {
        return redis.get(StrUtil.format(RedisKeyConstants.USER_TOKEN, uid), UserAuthorization.class);
    }
}
