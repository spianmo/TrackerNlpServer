package com.tracker.nlp.controller;

import com.tracker.nlp.base.BaseResponse;
import com.tracker.nlp.base.HttpStatus;
import com.tracker.nlp.config.mongo.MongoDBUtil;
import com.tracker.nlp.dto.MsgPack;
import com.tracker.nlp.dto.PageRequestDto;
import com.tracker.nlp.service.PublicOpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@Api(tags = "舆情关键字监测、LDA主题监测、词云生成")
public class PublicOpinionController {

    @Autowired
    PublicOpinionService publicOpinionService;

    @ApiOperation("插入要监测的舆情关键字")
    @PostMapping("/keyword/insert")
    BaseResponse<String> createKeyword(@RequestBody Map<String, String> keyword) {
        publicOpinionService.saveKeyword(keyword.get("keyword"));
        return new BaseResponse<>(HttpStatus.SUCCESS);
    }

    @ApiOperation("插入要监测的舆情主题关键字")
    @PostMapping("/lda/keyword/insert")
    BaseResponse<String> createLDAKeyword(@RequestBody Map<String, String> keyword) {
        publicOpinionService.saveThemeKeyword(keyword.get("keyword"));
        return new BaseResponse<>(HttpStatus.SUCCESS);
    }

    @ApiOperation("删除要监测的舆情关键字")
    @DeleteMapping("/keyword/insert")
    BaseResponse<String> removeKeyword(@RequestBody Map<String, String> payload) {
        publicOpinionService.delKeyword(payload.get("word"));
        return new BaseResponse<>(HttpStatus.SUCCESS);
    }

    @ApiOperation("删除要监测的舆情主题关键字")
    @DeleteMapping("/lda/keyword/insert")
    BaseResponse<String> removeLDAKeyword(@RequestBody Map<String, String> payload) {
        publicOpinionService.delThemeKeyword(payload.get("word"));
        return new BaseResponse<>(HttpStatus.SUCCESS);
    }

    @ApiOperation("获取分页舆情击中日志")
    @PostMapping("/log/page")
    BaseResponse<?> pageHitLog(@RequestBody PageRequestDto pageRequestDto) {
        return new BaseResponse<>(HttpStatus.SUCCESS, publicOpinionService.pageHitLog(pageRequestDto));
    }

    @ApiOperation("获取群动态词云列表")
    @GetMapping("/wordCloud")
    BaseResponse<?> getWordCloud(@RequestParam(required = false) String groupId) {
        return new BaseResponse<>(HttpStatus.SUCCESS, publicOpinionService.getWordCloud(groupId));
    }
}
