package com.tracker.nlp.controller;

import cn.hutool.core.util.ArrayUtil;
import com.tracker.nlp.annotation.Authorization;
import com.tracker.nlp.base.BaseResponse;
import com.tracker.nlp.base.HttpStatus;
import com.tracker.nlp.config.mongo.MongoDBUtil;
import com.tracker.nlp.dto.LoginDto;
import com.tracker.nlp.dto.MsgPack;
import com.tracker.nlp.service.PublicOpinionService;
import com.tracker.nlp.service.SentimentService;
import com.tracker.nlp.util.FileTypeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.xiaosheng.chnlp.AHANLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin
@Api(tags = "舆情客户端消息接收与分析")
public class PluginMsgController {

    @Autowired
    SentimentService sentimentService;
    @Autowired
    PublicOpinionService publicOpinionService;

    @ApiOperation("舆情客户端插件上传消息")
    @PostMapping("/plugin/upload")
    BaseResponse<String> upload(@RequestBody MsgPack msgPack) {
        msgPack.setSentimentDto(sentimentService.classify8(msgPack.getMsgContent()));
        MongoDBUtil.saveOne("msg", msgPack);
        //更新群聊元数据
        sentimentService.updateGroup(msgPack);
        //更新账号昵称
        sentimentService.updateAccount(msgPack);
        //检查是否Hit关键词舆情
        publicOpinionService.handleHitKeyword(msgPack);
        //检查是否Hit主题关键词
        publicOpinionService.handleHitThemeKeyword(msgPack);
        log.error(msgPack.toString());
        return new BaseResponse<>(HttpStatus.SUCCESS);
    }
}
