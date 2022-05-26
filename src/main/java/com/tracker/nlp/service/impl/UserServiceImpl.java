package com.tracker.nlp.service.impl;

import cn.edu.njnu.collection.backend.util.EntityChangeUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.PasswdStrength;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracker.nlp.config.mongo.MongoDBUtil;
import com.tracker.nlp.constant.LocalConstants;
import com.tracker.nlp.dto.ImportUserDto;
import com.tracker.nlp.dto.LoginDto;
import com.tracker.nlp.entity.UserAuthorization;
import com.tracker.nlp.exception.ParamVaildException;
import com.tracker.nlp.interceptor.AuthorizationInterceptor;
import com.tracker.nlp.mapper.UserMapper;
import com.tracker.nlp.pojo.User;
import com.tracker.nlp.service.AuthorizationService;
import com.tracker.nlp.service.UserService;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: UserServiceImpl.java
 * @LastModified: 2022/01/18 17:37:18
 */

/**
 * @ClassName UserServiceImpl
 * @Description 用户服务实现
 * @Author Mister-Lu
 * @Date 2022/1/15
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private
    UserMapper userMapper;
    @Autowired
    @Lazy
    AuthorizationService authorizationService;
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean login(LoginDto loginDto, HttpServletResponse response) {
        User user = getOne(new QueryWrapper<User>() {{
            eq("username", loginDto.getUsername());
        }});
        if (user == null) return false;
        boolean result = DigestUtils.md5Hex(loginDto.getPassword()).equals(user.getPassword());
        if (user.getBlocked()) throw new RuntimeException("账号暂时无法登录");
        if (result) {
            String token = authorizationService.generateToken(String.valueOf(user.getId()));
            response.addHeader(AuthorizationInterceptor.HEADER_KEY_AUTHORIZATION, token);
            response.addHeader(AuthorizationInterceptor.HEADER_KEY_UID, String.valueOf(user.getId()));
        }
        return result;
    }

    @Override
    public Boolean resetPassword(String userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!Objects.equals(user.getPassword(), DigestUtils.md5Hex(oldPassword))) {
            throw new ParamVaildException("旧密码不正确");
        }
        if (PasswdStrength.check(newPassword) < 4) throw new ParamVaildException("密码强度不够，请至少包含数字、大小写字母和其他符号");
        boolean result = update(null, new UpdateWrapper<User>() {
            {
                eq("id", userId);
                set("password", DigestUtils.md5Hex(newPassword));
            }
        });
        if (result) authorizationService.updateUserAuthorization(userId, true);
        return result;
    }

    @Override
    public User getUserAccountById(String id) {
        User user = getById(id);
        if (user == null) return null;
        return user;
    }

    @Override
    public Boolean updateUser(String userId, User user, Boolean withAdmin) {
        boolean result = update(null, new UpdateWrapper<User>() {
                    {
                        eq("id", userId);
                        if (!StrUtil.isEmpty(user.getName())) set("name", user.getName());
                        if (!StrUtil.isEmpty(user.getHeadImgUrl())) set("head_img_url", user.getHeadImgUrl());
                        if (!StrUtil.isEmpty(user.getMobile())) set("mobile", user.getMobile());
                        if (!StrUtil.isEmpty(user.getEmail())) set("email", user.getEmail());
                        if (withAdmin) if (!StrUtil.isEmpty(user.getPassword()))
                            set("password", DigestUtils.md5Hex(user.getPassword()));
                    }
                }
        );
        if (result) authorizationService.updateUserAuthorization(userId, withAdmin);
        return result;
    }


    @Override
    public Boolean blockUser(String uid, Boolean blocked) {
        boolean result = update(null, new UpdateWrapper<User>() {
            {
                eq("id", uid);
                set("blocked", blocked);
            }
        });
        if (result) authorizationService.kickUserLogin(uid);
        return result;
    }

    @Override
    public Boolean registerUser(ImportUserDto importUser) {
        User user = EntityChangeUtil.INSTANCE.transform(User.class, importUser);
        if (StrUtil.isEmpty(user.getHeadImgUrl())) user.setHeadImgUrl(LocalConstants.HEAD_DEFAULT_IMG);
        if (StrUtil.isEmpty(user.getName())) user.setName(LocalConstants.NAME_DEFAULT);
        user.setBlocked(false);
        user.setPassword(DigestUtils.md5Hex(StrUtil.isEmpty(user.getPassword()) ? LocalConstants.PASSWORD_DEFAULT : user.getPassword()));
        return save(user);
    }
}
