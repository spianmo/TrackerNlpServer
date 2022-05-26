package org.kirito666.sentiment;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Config {

    private static final String configFilePath = "sentiment_classification.properties";
    private static final Properties props = new Properties();

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader == null) loader = me.xiaosheng.chnlp.Config.class.getClassLoader();
            props.load(new InputStreamReader(loader.getResourceAsStream(configFilePath), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String CoreDictionary5Path() {
        return props.getProperty("CoreDictionary5Path");
    }

    public static String LSTMSentiment5Model() {
        return props.getProperty("LSTMSentiment5Model");
    }

    public static String[] SentimentLabel5Zh() {
        return props.getProperty("SentimentLabel5Zh").split(",");
    }

    public static String[] SentimentLabel5En() {
        return props.getProperty("SentimentLabel5En").split(",");
    }

    public static String CoreDictionary8Path() {
        return props.getProperty("CoreDictionary8Path");
    }

    public static String LSTMSentiment8Model() {
        return props.getProperty("LSTMSentiment8Model");
    }

    public static String[] SentimentLabel8Zh() {
        return props.getProperty("SentimentLabel8Zh").split(",");
    }

    public static String[] SentimentLabel8En() {
        return props.getProperty("SentimentLabel8En").split(",");
    }
}

