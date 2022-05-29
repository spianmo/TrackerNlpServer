package com.tracker.nlp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kirito666.sentiment.SentimentDto;
import org.springframework.data.annotation.Id;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MsgPack {
    @Id
    public String id;

    ChatDirection chatDirection;
    //消息类别
    ChatType chatType;
    //发送者UIN
    Long senderUid;
    //接收者UIN
    Long receiverUid;
    //发送者昵称
    String senderNickName;
    //接收者昵称
    String receiverNickName;
    //接收者聊天备注
    String contactShowName;
    //群ID
    Long groupId;
    //群名称
    String groupName;
    //群成员卡片名称
    String groupMemCardName;
    //消息内容
    String msgContent;
    //消息时间戳
    Long timeStamp;
    //格式化时间
    Date formatTime;

    SentimentDto sentimentDto;

    enum ChatDirection {
        Send, Receive
    };
    enum ChatType {
        Person, Group, Discuss
    };
}
