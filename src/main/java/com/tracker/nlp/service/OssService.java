package com.tracker.nlp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: OssService.java
 * @LastModified: 2021/11/10 15:34:10
 */

public interface OssService {
    String uploadFile(MultipartFile file) throws IOException;
}
