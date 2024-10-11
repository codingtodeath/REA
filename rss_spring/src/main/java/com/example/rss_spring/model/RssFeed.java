package com.example.rss_spring.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("rss源数据库字段")
public class RssFeed {

    @ApiModelProperty(value = "rss源所属序号ID", required = true, example = "1")
    private int id;

    @ApiModelProperty(value = "rss源名称", required = true, example = "阮一峰的博客")
    private String name;

    @ApiModelProperty(value = "rss源地址", required = true, example = "https://www.ruanyifeng.com/blog/atom.xml")
    private String url;

}
