package org.kirito666.sentiment;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 情感分析结果
 *
 * @author Finger
 */
@Data
public class SentimentDto {
    public double credit;
    public int labelCount;
    public List<Sentiment> labels;
    public List<Sentiment> predictedLabels;
    private static String[] LABELS_EN;
    private static String[] LABELS_ZH;

    static {
        LABELS_EN = Config.SentimentLabel5En();
        LABELS_ZH = Config.SentimentLabel5Zh();
    }

    private SentimentDto(){}

    public static SentimentDto analysis(float[] output) {
        SentimentDto sentimentDto = new SentimentDto();
        sentimentDto.labelCount = output.length;
        sentimentDto.labels = new ArrayList<>();
        int count = 0;
        for (float item : output) {
            String it = String.format("%.40f", item);
            sentimentDto.labels.add(new Sentiment(LABELS_ZH[count], LABELS_EN[count], item, it));
            count++;
        }
        return sentimentDto;
    }
}