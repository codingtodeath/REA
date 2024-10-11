package com.example.rss_spring.rssHandler;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import com.example.rss_spring.model.RssFeed;
import com.example.rss_spring.service.DataService;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

// 这个应该用数据库存储，看看怎么使用先
@Component
public class rssParser {
    @Getter
    @Autowired(required = false)
    private ArrayList<RssFeed> feedArray;

    @Autowired
    private DataService dataController;

    // 构造函数，读取数据库中所有的rss源
    public rssParser(){

    }

    // 使用 @PostConstruct 注解的方法来初始化 feedArray
    @PostConstruct
    public void init() {
        feedArray = new ArrayList<RssFeed>();
        try {
            feedArray.addAll(dataController.getAllFeeds());
        } catch (Exception e) {
            // 处理异常，例如记录日志
            e.printStackTrace();
        }
    }

    // 添加新的rss源
    public void addRss(int id, String name, String url){
        RssFeed new_feed = new RssFeed(id,name,url);
        dataController.insertFeed(id,name,url);  // 可能url的编码或者空格会有影响
        feedArray.add(new_feed);
    }

    // 删除一个rss源
    public void deleteRssById(int id){
        dataController.deleteFeedById(id);
        feedArray.removeIf(element -> element.getId() == id);  // 新的写法！！！
    }

    // 删除多个rss源
    public void deleteRsses(List<Integer> idList){
        for (int element : idList){
            deleteRssById(element);
        }
    }

    // 修改rss源，这里面的id是已经在数据库中的id
    public void updateRss(RssFeed feed){
        dataController.updateFeed(feed);
        for (RssFeed element : feedArray){
            if(element.getId()==feed.getId()){
                element.setName(feed.getName());
                element.setUrl(feed.getUrl());
            }
        }
    }


    // 解析一个rss源
    public SyndFeed parse(RssFeed rssToBeParse) throws Exception {
        // String url = "https://www.ruanyifeng.com/blog/atom.xml";
        return new SyndFeedInput().build(new XmlReader(new URL(rssToBeParse.getUrl())));
    }

}
