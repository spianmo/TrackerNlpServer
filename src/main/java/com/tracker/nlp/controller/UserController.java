package com.tracker.nlp.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tracker.nlp.annotation.Authorization;
import com.tracker.nlp.annotation.LoginUser;
import com.tracker.nlp.base.BaseResponse;
import com.tracker.nlp.base.HttpStatus;
import com.tracker.nlp.dto.ImportUserDto;
import com.tracker.nlp.dto.LoginDto;
import com.tracker.nlp.dto.PasswordResetDto;
import com.tracker.nlp.entity.UserAuthorization;
import com.tracker.nlp.exception.ParamVaildException;
import com.tracker.nlp.pojo.User;
import com.tracker.nlp.service.AuthorizationService;
import com.tracker.nlp.service.UserService;
import com.tracker.nlp.util.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: UserController.java
 * @LastModified: 2022/01/18 17:31:18fg
 */

/**
 * @ClassName UserController
 * @Description 用户接口
 * @Author Mister-Lu
 * @Date 2022/1/15
 **/
@RestController
@RequestMapping("/user")
@Api(value = "管理后台用户模块接口", tags = "User")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private AuthorizationService authorizationService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        if (StrUtil.isEmpty(loginDto.getUsername()) || StrUtil.isEmpty(loginDto.getPassword()))
            throw new ParamVaildException("关键参数缺失");
        if (loginDto.getPassword() == null) throw new ParamVaildException("非法登录");
        return userService.login(loginDto, response) ? BaseResponse.builder()
                .code(HttpStatus.SUCCESS.getCode())
                .data(authorizationService.getUserAuthorization(
                                String.valueOf(
                                        userService.getOne(new QueryWrapper<User>() {{
                                            eq("username", loginDto.getUsername());
                                        }}).getId())
                        )
                )
                .message("账号登录成功").build() : BaseResponse.builder()
                .code(HttpStatus.FAILD.getCode())
                .message("账号登录失败").build();
    }

    @ApiOperation("用户自行更新用户信息")
    @Authorization
    @PutMapping(value = "/update")
    public BaseResponse<?> updateUser(@RequestBody User user, @ApiIgnore @LoginUser UserAuthorization userAuthorization) {
        return BaseResponse.simple(() -> userService.updateUser(userAuthorization.getUid(), user, false),
                "更新用户信息成功", "更新用户信息失败");
    }

    @ApiOperation("用户主动获取自身信息")
    @Authorization
    @GetMapping(value = "/info")
    public BaseResponse<?> getSelfUserInfo(@ApiIgnore @LoginUser UserAuthorization userAuthorization) {
        return BaseResponse.simple(() -> userAuthorization,
                "获取信息成功", "获取信息失败");
    }

    @ApiOperation("用户修改密码")
    @Authorization
    @PutMapping(value = "/password/reset")
    public BaseResponse<?> resetPassword(@RequestBody PasswordResetDto passwordResetDto,
                                         @ApiIgnore @LoginUser UserAuthorization userAuthorization) {
        String newPassword = passwordResetDto.getNewPassword();
        String oldPassword = passwordResetDto.getOldPassword();
        if (StrUtil.isEmpty(oldPassword)) throw new ParamVaildException("旧的密码不能为空");
        if (StrUtil.isEmpty(newPassword)) throw new ParamVaildException("新的密码不能为空");
        if (!StrUtil.isEmpty(passwordResetDto.getEmail())) {
            userService.updateUser(userAuthorization.getUid(), User.builder()
                    .email(passwordResetDto.getEmail())
                    .build(), false);
        }
        return BaseResponse.simple(() ->
                        userService.resetPassword(
                                String.valueOf(userAuthorization.getUserAccount().getId()),
                                oldPassword,
                                newPassword),
                "修改成功", "修改失败");
    }


    @ApiOperation("管理员添加用户")
    @Authorization
    @PostMapping("/admin/addUser")
    public BaseResponse<?> registerUser(@RequestBody ImportUserDto importUser) {
        return BaseResponse.simple(() -> userService.registerUser(importUser),
                "添加成功", "添加失败");
    }

    @ApiOperation("管理员根据uid修改用户信息,包括密码")
    @Authorization
    @PutMapping(value = "/admin/updateUser")
    public BaseResponse<?> updateUserWithAdmin(@RequestBody User user) {
        return BaseResponse.simple(() -> userService.updateUser(String.valueOf(user.getId()), user, true),
                "更新用户信息成功", "更新用户信息失败");
    }

    @ApiOperation("管理员根据uid封锁用户")
    @Authorization
    @PostMapping(value = "/admin/blockUser")
    public BaseResponse<?> blockUserWithAdmin(@RequestParam String uid, @RequestParam Boolean blocked) {
        return BaseResponse.simple(() -> userService.blockUser(uid, blocked),
                "操作成功", "操作失败");
    }

}

