package com.tracker.nlp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hankcs.hanlp.seg.common.Term;
import com.tracker.nlp.config.mongo.MongoDBUtil;
import com.tracker.nlp.dto.IMAccount;
import com.tracker.nlp.dto.IMGroup;
import com.tracker.nlp.dto.MsgPack;
import com.tracker.nlp.service.SentimentService;
import me.xiaosheng.chnlp.AHANLP;
import org.apache.commons.io.FileUtils;
import org.kirito666.sentiment.Config;
import org.kirito666.sentiment.SentimentDto;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SentimentServiceImpl implements SentimentService {

    private static final Map<String, Integer> VECTOR_DICTIONARY = new HashMap<>();
    private static final Graph model;

    static {
        readDict();
        byte[] graphDef = readAllBytesOrExit(Paths.get(Config.LSTMSentiment8Model()));
        model = new Graph();
        model.importGraphDef(graphDef);
    }

    public static Tensor<?> transformer(String str, Map<String, Integer> vectorDict) {
        float[][] input = new float[1][150];
        List<Term> nlpSegResult = AHANLP.NLPSegment(str);
        int count = 0;
        for (Term term : nlpSegResult) {
            input[0][count++] = vectorDict.getOrDefault(term.word, 0);
        }
        return Tensor.create(input);
    }

    public static void readDict() {
        List<String> s;
        try {
            s = FileUtils.readLines(new File(Config.CoreDictionary8Path()), "UTF-8");
            for (String str : s) {
                String key = str.split(":")[0];
                int value = Integer.parseInt(str.split(":")[1]);
                VECTOR_DICTIONARY.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: "
                    + e.getMessage());
            System.exit(1);
        }
        return null;
    }


    @Override
    public SentimentDto classify8(String inputText) {
        Tensor<?> input = transformer(inputText, VECTOR_DICTIONARY);
        System.out.println(input);
        try (Session s = new Session(model);
             Tensor<?> output = s.runner().feed("Input:0", input).fetch("Identity:0").run().get(0)) {
            long[] shape = output.shape();
            int labels = (int) shape[1];
            float[][] copy = new float[1][labels];
            output.copyTo(copy);
            float[] result = copy[0];
            return SentimentDto.analysis(result);
        }
    }

    @Override
    public boolean isExistGroup(String groupNum) {
        Long result = MongoDBUtil.countRangeCondition(IMGroup.class, "group", new Criteria(), new LinkedHashMap<String, Object>() {{
            put("_id", groupNum);
        }});
        return result > 0;
    }

    @Override
    public boolean isExistIMAccount(String accountNum) {
        Long result = MongoDBUtil.countRangeCondition(IMAccount.class, "im_account", new Criteria(), new LinkedHashMap<String, Object>() {{
            put("_id", accountNum);
        }});
        return result > 0;
    }

    @Override
    public void updateGroup(MsgPack msgPack) {
        String groupNum = String.valueOf(msgPack.getGroupId());
        if (!StrUtil.isEmpty(groupNum) && !groupNum.equals("0")) {
            MongoDBUtil.saveOne("group", IMGroup.builder()
                    .groupId(groupNum)
                    .groupName(msgPack.getGroupName())
                    .build());
        }
    }

    @Override
    public void updateAccount(MsgPack msgPack) {
        String senderUid = String.valueOf(msgPack.getSenderUid());
        String receiverUid = String.valueOf(msgPack.getReceiverUid());
        if (!StrUtil.isEmpty(senderUid) && !senderUid.equals("0")) {
            MongoDBUtil.saveOne("im_account", IMAccount.builder()
                    .accountId(senderUid)
                    .nickName(msgPack.getSenderNickName())
                    .build());
        }

        if (!StrUtil.isEmpty(receiverUid) && !receiverUid.equals("0")) {
            MongoDBUtil.saveOne("im_account", IMAccount.builder()
                    .accountId(receiverUid)
                    .nickName(msgPack.getReceiverNickName())
                    .build());
        }
    }
}
