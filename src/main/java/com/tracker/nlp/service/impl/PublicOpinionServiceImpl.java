package com.tracker.nlp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.tracker.nlp.config.mongo.MongoDBUtil;
import com.tracker.nlp.config.mongo.PageModel;
import com.tracker.nlp.dto.*;
import com.tracker.nlp.service.MailService;
import com.tracker.nlp.service.PublicOpinionService;
import com.tracker.nlp.vo.PageResponseVo;
import me.xiaosheng.chnlp.AHANLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class PublicOpinionServiceImpl implements PublicOpinionService {
    @Autowired
    MailService mailService;
    @Override
    public void saveThemeKeyword(String word) {
        MongoDBUtil.saveOne("lda_keywords", TrTheme.builder()
                .word(word)
                .build());
    }

    @Override
    public void saveKeyword(String word) {
        MongoDBUtil.saveOne("keywords", TrKeyword.builder()
                .word(word)
                .build());
    }

    @Override
    public void saveHitLog(HitLog log) {
        MongoDBUtil.saveOne("hit_log", log);
    }

    @Override
    public void delThemeKeyword(String word) {
        MongoDBUtil.removeAllByParam("word", word, "lda_keywords");
    }

    @Override
    public void delKeyword(String word) {
        MongoDBUtil.removeAllByParam("word", word, "keywords");
    }

    @Override
    public void handleHitThemeKeyword(MsgPack msgPack) {
        String sentence = msgPack.getMsgContent();
        AtomicBoolean flag = new AtomicBoolean(false);
        List<String> hitWords = new ArrayList<>();
        AHANLP.StandardSegment(sentence, true).forEach(item->{
            Long result = MongoDBUtil.countRangeCondition(TrTheme.class, "lda_keywords", new Criteria(), new LinkedHashMap<String, Object>() {{
                put("word", item.word);
            }});
            if (result > 0) {
                flag.set(true);
                hitWords.add(item.word);
            }
        });
        if (flag.get()) {
            String title = String.format("[%s] %s 于%s 触发了主题舆情预警 %s",
                    msgPack.getSenderUid(), msgPack.getSenderNickName(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    Arrays.toString(hitWords.toArray()));
            String content = String.format("[消息详情] %s", msgPack);
            saveHitLog(HitLog.builder()
                    .title(title)
                    .content(content)
                    .time(new Date())
                    .msgPack(msgPack)
                    .build());
            mailService.sendTextMail("finger@spianmo.com", title, content);
        }
    }

    @Override
    public void handleHitKeyword(MsgPack msgPack) {
        String sentence = msgPack.getMsgContent();
        AtomicBoolean flag = new AtomicBoolean(false);
        List<String> hitWords = new ArrayList<>();
        AHANLP.StandardSegment(sentence, false).forEach(item->{
            Long result = MongoDBUtil.countRangeCondition(TrKeyword.class, "keywords", new Criteria(), new LinkedHashMap<String, Object>() {{
                put("word", item.word);
            }});
            if (result > 0) {
                flag.set(true);
                hitWords.add(item.word);
            }
        });
        if (flag.get()) {
            String title = String.format("[%s] %s 于%s 触发了关键词舆情预警 %s",
                    msgPack.getSenderUid(), msgPack.getSenderNickName(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    Arrays.toString(hitWords.toArray()));
            String content = String.format("[消息详情] %s", msgPack);
            saveHitLog(HitLog.builder()
                    .title(title)
                    .content(content)
                    .time(new Date())
                    .msgPack(msgPack)
                    .build());
            mailService.sendTextMail("finger@spianmo.com", title, content);
        }
    }

    @Override
    public PageResponseVo<HitLog> pageHitLog(PageRequestDto pageDto) {
        Integer pageId = pageDto.getCurrentPage();
        Integer pageSize = pageDto.getPageSize();
        PageResponseVo<HitLog> result = new PageResponseVo<>();
        PageModel page = MongoDBUtil.findSortPageCondition(
                HitLog.class, "hit_log", new LinkedHashMap<String, Object>() {{
                }}, pageId, pageSize,
                Sort.Direction.DESC, "time");
        result.setTotal(Math.toIntExact(page.getTotal()));
        result.setRows(page.getList());
        return result;
    }

    @Override
    public List<String> getWordCloud(String groupId) {
        List<String> sentences = new ArrayList<>();
        MongoDBUtil.findSortByParamLimit(MsgPack.class, "msg", new LinkedHashMap<String, Object>(){{
            if (groupId != null && !StrUtil.isEmpty(groupId) && !groupId.equals("0")){
                put("groupId", groupId);
            }
        }}, "formatTime", Sort.Direction.DESC, 50).forEach(item->{
            MsgPack pkg = (MsgPack) item;
            sentences.add(pkg.getMsgContent());
        });
        return AHANLP.getWordList(AHANLP.StandardSegment(Arrays.toString(sentences.stream().distinct().toArray()), true));
    }
}
