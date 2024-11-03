package com.example.rss_spring.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("rss源数据库字段")
public class Article {

    @ApiModelProperty(value = "文章所属序号ID", required = true, example = "1")
    private int id;

    @ApiModelProperty(value = "文章标题", required = true, example = "科技爱好者周刊（第 320 期）：乒乓仓")
    private String title;

    @ApiModelProperty(value = "文章摘要", required = true, example = "这里记录每周值得分享的科技内容，周五发布。...")
    private String description;

    @ApiModelProperty(value = "文章原地址", required = true, example = "http://www.ruanyifeng.com/blog/2024/10/weekly-issue-320.html")
    private String url;

    @ApiModelProperty(value = "文章作者", required = true, example = "阮一峰")
    private String author;

    @ApiModelProperty(value = "文章发布时间", required = true, example = "2024-10-11 08:11:11")
    private String time;

    @ApiModelProperty(value = "文章内容", required = true, example = "。。。。。。")
    private String content;

    @ApiModelProperty(value = "是否收藏", required = true, example = "1")
    private String collect;
}
