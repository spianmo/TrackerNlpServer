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
 * @FileName: ImportUserDto.java
 * @LastModified: 2022/01/18 16:22:18
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportUserDto {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "名称", required = false)
    private String name;
    @ApiModelProperty(value = "手机号码", required = false)
    private String mobile;
    @ApiModelProperty(value = "密码", required = false)
    private String password;
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;
}
