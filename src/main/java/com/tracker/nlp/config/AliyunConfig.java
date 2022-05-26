package com.tracker.nlp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: AliyunConfig.java
 * @LastModified: 2021/11/10 15:34:10
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunConfig {
    public String endPoint;
    public String keyId;
    public String accesskeySecret;
    public String bucketName;
    public String refHost;
    public String refPath;
}
