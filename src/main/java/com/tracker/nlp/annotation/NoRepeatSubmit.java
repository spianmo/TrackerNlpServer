package com.tracker.nlp.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Copyright (c) 2022
 * 不允许重复提交注解
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: NoRepeatSubmit.java
 * @LastModified: 2022/01/15 17:39:15
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

}
