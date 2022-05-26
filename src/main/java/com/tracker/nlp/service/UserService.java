package com.tracker.nlp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tracker.nlp.dto.ImportUserDto;
import com.tracker.nlp.dto.LoginDto;
import com.tracker.nlp.pojo.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: UserService.java
 * @LastModified: 2022/01/18 17:31:18
 */

/**
 * @ClassName UserService
 * @Description 用户服务
 * @Author Mister-Lu
 * @Date 2022/1/15
 **/
public interface UserService extends IService<User> {

    /**
     * 根据登陆对象的用户名和密码校验是否匹配，并响应http请求
     *
     * @param loginDto 登录对象
     * @param response http响应
     * @return 校验结果是否一致，并将响应添加到cookie中
     */
    boolean login(LoginDto loginDto, HttpServletResponse response);


    /**
     * 根据用户ID修改密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return 是否修改密码成功
     */
    Boolean resetPassword(String userId, String oldPassword, String newPassword);

    /**
     * 根据用户id查询用户信息
     *
     * @param id 用户id
     * @return UserAccountVo视图对象
     */
    User getUserAccountById(String id);

    /**
     * 用户自行更新用户信息
     *
     * @param userId    用户id
     * @param user      用户实体
     * @param withAdmin 携带管理员角色信息
     * @return 是否更新完成
     */
    Boolean updateUser(String userId, User user, Boolean withAdmin);


    /**
     * 根据uid封锁用户
     *
     * @param uid     用户id
     * @param blocked 封锁
     * @return 更新的用户信息
     */
    Boolean blockUser(String uid, Boolean blocked);

    /**
     * 添加用户
     *
     * @param importUser 添加用户信息
     * @return 是否注册的flag
     */
    Boolean registerUser(ImportUserDto importUser);

}
