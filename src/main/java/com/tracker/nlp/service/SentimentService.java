package com.tracker.nlp.service;

import com.tracker.nlp.dto.MsgPack;
import org.kirito666.sentiment.SentimentDto;

public interface SentimentService {
    SentimentDto classify8(String inputText);

    boolean isExistGroup(String groupNum);

    boolean isExistIMAccount(String accountNum);

    void updateGroup(MsgPack msgPack);

    void updateAccount(MsgPack msgPack);
}
