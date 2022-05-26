package com.tracker.nlp.advice;

import com.google.gson.Gson;
import com.tracker.nlp.base.BaseResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: ResponseAdvice.java
 * @LastModified: 2021/11/13 17:13:13
 */


@Component
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class converterType) {
        return returnType.getDeclaringClass().equals(Docket.class);
    }


    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType, @NotNull MediaType selectedContentType, @NotNull Class selectedConverterType, @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        if (body instanceof BaseResponse || body instanceof ResponseEntity) {
            return body;
        } else if (body instanceof String) {
            return new Gson().toJson(BaseResponse.success(body));
        }
        return BaseResponse.success(body);
    }

}
