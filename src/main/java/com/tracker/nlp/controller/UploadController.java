package com.tracker.nlp.controller;

import cn.hutool.core.util.ArrayUtil;
import com.tracker.nlp.annotation.Authorization;
import com.tracker.nlp.base.BaseResponse;
import com.tracker.nlp.base.HttpStatus;
import com.tracker.nlp.service.OssService;
import com.tracker.nlp.util.FileTypeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: UploadController.java
 * @LastModified: 2022/04/02 00:30:02
 */

@Slf4j
@RestController
@CrossOrigin
@Api(tags = "OSS文件上传接口")
@RequestMapping("/upload")
public class UploadController {
    @Resource(name = "${upload.ossType}")
    private OssService ossService;
    @Value("${upload.image}")
    private String[] allowImageType;

    @ApiOperation("上传图片获取直链")
    @Authorization
    @PostMapping("pic")
    BaseResponse<String> uploadImage(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        if (uploadFile.getSize() == 0) {
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), "上传文件为空");
        }
        String exif = FileTypeUtils.getFileTypeByMagicNumber(uploadFile.getInputStream());
        if (!ArrayUtil.contains(allowImageType, exif)) {
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), "请上传图片");
        }
        if (uploadFile.getSize() > 2 * 1024 * 1024) {
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), "请上传2M以内的图片");
        }
        String url = ossService.uploadFile(uploadFile);
        return new BaseResponse<>(HttpStatus.SUCCESS, url);
    }

    @ApiOperation("上传文件获取直链")
    @Authorization
    @PostMapping("file")
    public BaseResponse<String> uploadOssFile(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        if (uploadFile.getSize() == 0) {
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), "上传文件为空");
        }
        if (uploadFile.getSize() > 10 * 1024 * 1024) {
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), "请上传10M以内的文件");
        }
        String url = ossService.uploadFile(uploadFile);
        return new BaseResponse<>(HttpStatus.SUCCESS, url);
    }
}
