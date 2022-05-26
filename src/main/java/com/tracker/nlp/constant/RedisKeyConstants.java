package com.tracker.nlp.constant;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: RedisKeyConstants.java
 * @LastModified: 2022/01/15 18:16:15
 */

public interface RedisKeyConstants {
    /**
     * 用户token
     */
    String USER_TOKEN = "user:token:{}";
    /**
     * 标注请求重复的Code
     */
    String REPEAT_CODE = "repeat:{}:{}";

    class Util {
        public static String TODAY() {
            return DateUtil.format(new Date(), "yyyy-MM-dd");
        }
    }
}
