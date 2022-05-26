package com.tracker.nlp.exception;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: InsufficAuthException.java
 * @LastModified: 2021/11/10 15:34:10
 */

public class InsufficAuthException extends RuntimeException {
    public InsufficAuthException(String error) {
        super(error);
    }
}
