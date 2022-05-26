package com.tracker.nlp.entity;

import com.tracker.nlp.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: UserAuthorization.java
 * @LastModified: 2022/01/14 19:32:14
 */

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserAuthorization {
    String uid;
    String token;
    User userAccount;
}
