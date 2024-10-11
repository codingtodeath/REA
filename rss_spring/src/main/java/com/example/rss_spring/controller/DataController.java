package com.example.rss_spring.controller;

import com.example.rss_spring.model.Article;
import com.example.rss_spring.model.RssFeed;
//import com.example.rss_spring.rssHandler.rssParser;
import com.example.rss_spring.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "API接口")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class DataController {
//    @Autowired
//    private rssParser rssparser;

    @Autowired
    private DataService dataService;

    @ApiOperation("添加新的Rss源")
    @PostMapping("/insertFeed")
    public void insert(@RequestBody RssFeed data){
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        dataService.insertFeed(data.getName(), data.getUrl());
    }

    @ApiOperation("得到所有的Rss源")
    @GetMapping("/getAllRss")
    public ArrayList<RssFeed> getAllRss() {
        return dataService.getAllFeeds();
    }

//    @ApiOperation("解析点中的rss源")
//    @GetMapping("/parse")
//    public ArrayList<RssFeed> parse() {
//        return rssparser.getFeedArray();
//    }
//
//    @ApiOperation("更新Rss源")
//    @PutMapping("/update")
//    public void update(@RequestBody RssFeed data) {
//        rssparser.updateRss(data);
//    }
//
//    @ApiOperation("删除指定id的Rss源")
//    @DeleteMapping("/delete/{id}")
//    public void deleteUser(@PathVariable int id) {
//        rssparser.deleteRssById(id);
//    }


    @ApiOperation("查看所有文章")
    @GetMapping("/getAllArticlesByTime")
    public ArrayList<Article> getAllArticlesByTime() {
        return dataService.getAllArticlesByTime();
    }
}

