package com.tracker.nlp.service;


import com.tracker.nlp.entity.UserAuthorization;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: AuthorizationService.java
 * @LastModified: 2022/01/18 01:58:18
 */

public interface AuthorizationService {
    /**
     * 根据Uid和Token检查是否处于登录态
     *
     * @param uid   用户ID
     * @param token 前端Request Header中的Authorization字段
     * @return 是否处于登录态。Redis中的K-V查询
     */
    boolean checkLogin(String uid, String token);

    /**
     * 登录时调用，生成Token
     *
     * @param uid 用户ID
     * @return 登录时调用，Redis生成Token，Key为Uid
     */
    String generateToken(String uid);

    /**
     * 刷新Redis中对应uid的UserAuthorization
     *
     * @param uid   用户ID
     * @param force 是否强制重新登录
     */
    void updateUserAuthorization(String uid, boolean force);

    /**
     * 踢除用户登录态
     *
     * @param uid 用户ID
     */
    void kickUserLogin(String uid);

    /**
     * 根据Key：Uid获取Redis中对应的Value：UserAuthorization
     *
     * @param uid 用户ID
     * @return UserAuthorization
     */
    UserAuthorization getUserAuthorization(String uid);
}
