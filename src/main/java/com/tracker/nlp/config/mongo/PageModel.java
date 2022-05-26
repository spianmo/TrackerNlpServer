package com.tracker.nlp.config.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: PageModel.java
 * @LastModified: 2022/01/12 21:20:12
 */

@Data
@ToString
@NoArgsConstructor
public class PageModel implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 当前页条数
     */
    private Integer pageSize = 10;
    /**
     * 总共的条数
     */
    private Long total;
    /**
     * 总共的页数
     */
    private Integer pages;
    /**
     * 实体类集合
     */
    private List list;

}