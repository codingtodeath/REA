package com.example.rss_spring.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("收藏数据展示结构")
public class collectArticle {
    @ApiModelProperty(value = "文章所属序号ID", required = true, example = "1")
    private int id;

    @ApiModelProperty(value = "文章标题", required = true, example = "科技爱好者周刊（第 320 期）：乒乓仓")
    private String name;

}
