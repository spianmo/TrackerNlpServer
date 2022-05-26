package com.tracker.nlp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: PasswordResetDto.java
 * @LastModified: 2022/01/17 13:21:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDto {
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;
}
