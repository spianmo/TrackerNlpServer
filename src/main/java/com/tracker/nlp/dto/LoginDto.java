package com.tracker.nlp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserDto
 * @Description 账号密码登录
 * @Author Mister-Lu
 * @Date 2022/1/15
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
