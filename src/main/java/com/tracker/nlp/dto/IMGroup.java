package com.tracker.nlp.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class IMGroup {
    @Id
    public String groupId;
    public String groupName;
}
