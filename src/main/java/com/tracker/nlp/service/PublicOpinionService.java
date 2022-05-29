package com.tracker.nlp.service;

import com.tracker.nlp.dto.HitLog;
import com.tracker.nlp.dto.MsgPack;
import com.tracker.nlp.dto.PageRequestDto;
import com.tracker.nlp.vo.PageResponseVo;

import java.util.List;

public interface PublicOpinionService {

    void saveThemeKeyword(String word);

    void saveKeyword(String word);

    void saveHitLog(HitLog log);

    void delThemeKeyword(String word);

    void delKeyword(String word);

    void handleHitThemeKeyword(MsgPack msgPack);

    void handleHitKeyword(MsgPack msgPack);

    PageResponseVo<HitLog> pageHitLog(PageRequestDto pageRequestDto);

    List<String> getWordCloud(String groupId);
}
