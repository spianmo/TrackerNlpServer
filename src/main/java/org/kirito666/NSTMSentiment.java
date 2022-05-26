package org.kirito666;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hankcs.hanlp.seg.common.Term;
import me.xiaosheng.chnlp.AHANLP;
import org.apache.commons.io.FileUtils;
import org.kirito666.sentiment.Config;
import org.kirito666.sentiment.SentimentDto;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * @author Finger
 */
public class NSTMSentiment {
    private static final Map<String, Integer> VECTOR_DICTIONARY = new HashMap<>();
    private static final Graph model;

    static {
        readDict();
        byte[] graphDef = readAllBytesOrExit(Paths.get(Config.LSTMSentiment5Model()));
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
        System.out.println("Word2VectorDimInput:");
        System.out.println(Arrays.toString(input[0]));
        return Tensor.create(input);
    }

    public static void readDict() {
        List<String> s;
        try {
            s = FileUtils.readLines(new File(Config.CoreDictionary5Path()), "UTF-8");
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

    public static SentimentDto classify5(String inputText){
        Tensor<?> input = transformer(inputText, VECTOR_DICTIONARY);
        System.out.println(input);
        try (Session s = new Session(model);
             Tensor<?> output = s.runner().feed("Input:0", input).fetch("Identity:0").run().get(0)) {
            long[] shape = output.shape();
            int labels = (int) shape[1];
            float[][] copy = new float[1][labels];
            output.copyTo(copy);
            float[] result = copy[0];
            SentimentDto sentimentDto = SentimentDto.analysis(result);
            System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(sentimentDto));
            return sentimentDto;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String lineContent = scanner.nextLine();
            classify5(lineContent);
        }

    }
}
