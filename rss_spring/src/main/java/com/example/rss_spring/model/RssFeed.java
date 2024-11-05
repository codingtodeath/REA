package com.example.rss_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "name", required = true, example = "阮一峰的博客")
    @JsonProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "url", required = true, example = "https://www.ruanyifeng.com/blog/atom.xml")
    @JsonProperty(value = "url")
    private String url;

}
