package org.kirito666;

import me.xiaosheng.chnlp.AHANLP;
import me.xiaosheng.chnlp.addition.WordCloud;

import java.io.IOException;
import java.util.List;

public class NLP {
    public static void main(String[] args) {
        /*String content = "苏州大学的周国栋教授正在教授自然语言处理课程。";
// 标准分词
        List<Term> stdSegResult = AHANLP.StandardSegment(content);
        System.out.println(stdSegResult);
//[苏州大学/ntu, 的/ude1, 周/qt, 国栋/nz, 教授/nnt, 正在/d, 教授/nnt, 自然语言处理/nz, 课程/n, 。/w]

// NLP分词
        List<Term> nlpSegResult = AHANLP.NLPSegment(content);
        System.out.println(nlpSegResult);

        System.out.println(Config.word2vecModelPath());*/

        String document = "苏州大学的周国栋教授正在教授自然语言处理课程";
        List<String> senList = AHANLP.extractKeySentence(document, 5);
        System.out.println("Key Sentences: ");
        for (int i = 0; i < senList.size(); i++)
            System.out.println((i + 1) + " : " + senList.get(i));
        System.out.println("Summary: ");
        System.out.println(AHANLP.extractSummary(document, 50));


        List<String> wordList = AHANLP.getWordList(AHANLP.StandardSegment(document, true));
        WordCloud wc = new WordCloud(wordList);
        try {
            wc.createImage("D:\\test.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
