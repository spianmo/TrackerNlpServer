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

        String document = "作为一个在这儿五年的学生，我也很想说说我的内心话。说实话，现在这所南京工业职业技术大学完全不是我以前认识的南京工业职业技术学院了。无限期封校并且请假困难，不承认转本生的四六级和计算机一级成绩还要重考，转本新生军训加晚自习，转本生到大四还是满课表的课，还有各种非常麻烦的琐事，可以说是把形式主义做到了极致。我不知道这是为什么，这些都是我未曾在南工院所经历的事情。我不知道是不是学院不同的原因，我曾经也是班长，曾经我们的班长群通知明确简洁明了，办事效率很高，一周也不会有太多事情，但转本后我也做了一学年班长，那真的是忙的爆炸，每天班长群就没消停过，各种大事小事，明明可以线上说一下就解决的事，非要喊大家去开会。真的是很折磨人。我很不理解现在为什么要把形式主义做的这么过分，难道就是为了面子?那这学校真的是烂透了，还考虑后面的招生嘛?这样下去我觉得我可以说我毕业于南京形式主义大学了。毫不客气的说，现在我所遇到的学校方面的问题，都未曾在南京工业职业技术学院发生过，怀念曾经的南京工业职业技术学院，也希望南京工业职业技术大学赶紧好起来。";
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
