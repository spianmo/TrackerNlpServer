package com.tracker.nlp.exception;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: MissingParamException.java
 * @LastModified: 2021/11/10 15:34:10
 */

public class MissingParamException extends RuntimeException {
    public MissingParamException(String error) {
        super(error);
    }
}
