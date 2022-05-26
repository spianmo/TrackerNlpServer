package com.tracker.nlp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Copyright (c) 2021
 *
 * @Project: hmmm-master
 * @Author: Finger
 * @FileName: PageDto.java
 * @LastModified: 2021/12/30 23:24:30
 */

@Data
public class PageRequestDto {

    @ApiModelProperty(value = "当前页", required = true)
    private Integer currentPage;

    @ApiModelProperty(value = "每页展示条数", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "查询条件，需要做模糊查询", required = false)
    private String queryString;
}
