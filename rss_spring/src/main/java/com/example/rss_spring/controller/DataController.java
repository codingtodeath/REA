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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Api(tags = "API接口")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin
public class DataController {
//    @Autowired
//    private rssParser rssparser;

    @Autowired
    private DataService dataService;

//    @ApiOperation("添加新的Rss源")
//    @PostMapping("/insertFeed")
//    public void insert(@RequestBody RssFeed data){
//        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
//        System.out.println(data);
//        dataService.insertFeed(data.getName(), data.getUrl());
//    }
//    @CrossOrigin(origins = "http://localhost:8086")
//    @ApiOperation("添加新的Rss源")
//    @PostMapping("/insertFeed/{name}/{url}")
//    public void insert(@PathVariable String name,@PathVariable String url){
//        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
//        System.out.println(name);
//        System.out.println(url);
//        dataService.insertFeed(name, url);
//    }
    @CrossOrigin(origins = "http://localhost:8086")
    @ApiOperation("添加新的Rss源")
    @PostMapping("/insertFeed")
    public void insert(@RequestParam String name,
                       @RequestParam String url){
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        System.out.println(name);
        System.out.println(url);
        dataService.insertFeed(name, url);
    }

    @CrossOrigin(origins = "http://localhost:8086")
    @ApiOperation("添加新的Rss源")
    @PostMapping("/deleteFeed")
    public void deletefeed(@RequestParam int id){
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        System.out.println(id);
        dataService.deleteFeedById(id);
    }

    @ApiOperation("得到所有的Rss源")
    @GetMapping("/getAllRss")
    public ArrayList<RssFeed> getAllRss() {
        return dataService.getAllFeeds();
    }


    @ApiOperation("上传信息接口")
    @PutMapping("/Add/{name}/{url}")
    public void addRss(@PathVariable String name, @PathVariable String url) {
        String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);
        dataService.insertFeed(name,decodedUrl);
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

    @ApiOperation("查看某一篇文章")
    @GetMapping("/getContentById")
    public String getContentById(@RequestParam int id) {
        return dataService.getContentById(id);
    }

    @ApiOperation("收藏或取消收藏某一篇文章")
    @GetMapping("/updateArticleCollect")
    public void updateArticleCollect(@RequestParam int id, @RequestParam int collect) {
        dataService.updateArticleCollect(id, collect);
    }

    @ApiOperation("查看某一篇文章的收藏状态")
    @GetMapping("/getArticleCollectById")
    public int getArticleCollectById(@RequestParam int id) {
        return dataService.getArticleCollectById(id);
    }

}

