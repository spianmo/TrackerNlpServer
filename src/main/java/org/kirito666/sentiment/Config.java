package org.kirito666.sentiment;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Config {

    private static String configFilePath = "sentiment_classification.properties";
    private static Properties props = new Properties();

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader == null) loader = me.xiaosheng.chnlp.Config.class.getClassLoader();
            props.load(new InputStreamReader(loader.getResourceAsStream(configFilePath), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String CoreDictionaryPath() {
        return props.getProperty("CoreDictionaryPath");
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
}

