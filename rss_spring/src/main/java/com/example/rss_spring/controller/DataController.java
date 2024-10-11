package com.example.rss_spring.controller;

import com.example.rss_spring.model.RssFeed;
import com.example.rss_spring.rssHandler.rssParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Api(tags = "API接口")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class DataController {
    @Autowired
    private rssParser rssparser;

    @ApiOperation("添加新的Rss源")
    @PostMapping("/insert")
    public void insert(@RequestBody RssFeed data) {
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        rssparser.addRss(data.getId(), data.getName(), data.getUrl());
    }

    @ApiOperation("得到所有的Rss源")
    @GetMapping("/findAll")
    public ArrayList<RssFeed> findAll() {
        return rssparser.getFeedArray();
    }

    @ApiOperation("更新Rss源")
    @PutMapping("/update")
    public void update(@RequestBody RssFeed data) {
        rssparser.updateRss(data);
    }

    @ApiOperation("删除指定id的Rss源")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        rssparser.deleteRssById(id);
    }

}

