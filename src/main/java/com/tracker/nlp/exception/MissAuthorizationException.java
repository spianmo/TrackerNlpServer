package com.tracker.nlp.exception;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: MissAuthorizationException.java
 * @LastModified: 2021/11/10 15:34:10
 */

public class MissAuthorizationException extends RuntimeException {
    public MissAuthorizationException(String message) {
        super(message);
    }
}
