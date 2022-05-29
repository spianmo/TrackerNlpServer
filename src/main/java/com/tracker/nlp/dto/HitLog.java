package com.tracker.nlp.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class HitLog {
    @Id
    String id;
    String title;
    String content;
    MsgPack msgPack;
    Date time;
}
