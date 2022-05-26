package com.tracker.nlp.annotation;

import com.tracker.nlp.entity.UserAuthorization;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (c) 2021
 *
 * @Project:FreyaServer
 * @Author:Finger
 * @FileName:RequireLogin.java
 * @LastModified:2021-04-02T09:20:11.638+08:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}
