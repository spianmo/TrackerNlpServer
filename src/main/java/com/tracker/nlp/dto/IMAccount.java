package com.tracker.nlp.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class IMAccount {
    @Id
    public String accountId;
    public String nickName;
}
