package com.tracker.nlp.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class TrTheme {
    @Id
    public String id;
    public String word;
}
