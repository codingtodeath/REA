package com.example.rss_spring.service;

import com.example.rss_spring.mapper.DataMapper;
import com.example.rss_spring.model.Article;
import com.example.rss_spring.model.RssFeed;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.net.URL;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

@Service
public class DataService {
    @Autowired(required = false)
    private DataMapper dataMapper;


//    @Data
    private ArrayList<RssFeed> feedArray;
//
//    @Data
    private ArrayList<Article> articleArray;

    public DataService(){

    }

    // 使用 @PostConstruct 注解的方法来初始化 feedArray
    @PostConstruct
    public void init() {
        feedArray = getAllFeeds();
        for(RssFeed element:feedArray) {
            parseFeed(element.getUrl());
        }
    }

    public void parseFeed(String url) {
        SyndFeed parseFeed;
        System.out.println("parsing......   ");
        try {
            parseFeed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (Exception e) {
            return;
        }
        List<SyndEntry> list = parseFeed.getEntries();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //遍历这个rss源的所有文章
        for (SyndEntry element : list) {
            String time = simpleDateFormat.format(element.getPublishedDate());
            System.out.println(element.getAuthor());
            if (!dataMapper.isArticleHere(element.getAuthor(),time)) {
                String descript = element.getDescription().getValue();
                String answer = descript;
                if (descript.length() > 20) {
                    if(descript.startsWith("<")){
                        answer = descript.split(">")[1];
                    }
                }
                if(answer.length() > 20){
                    answer = answer.substring(0,20);
                }
//            try {
//                if (!dataMapper.isArticleHere(element.getTitle())) {
//                    if (descript.length() > 20) {
//                        try {
//                            answer = chatLLm.chat("请为我概括以下内容（可能为html文件），不超过20字：" + descript.substring(0, 100));
//                        } catch (Exception e) {
//                            answer = descript.split(">")[0].substring(0, 20);
//                        }
//                    }
//                    // 转换时间
//                    dataMapper.insertArticle(element.getTitle(), answer, element.getLink(), element.getAuthor(), simpleDateFormat.format(element.getPublishedDate()));
//                }
//            } catch (Exception e) {
//                if (descript.length() > 20) {
//                    try {
//                        answer = chatLLm.chat("请为我概括以下内容（可能为html文件），不超过20字：" + descript.substring(0, 100));
//                    } catch (Exception ex) {
//                        System.out.println(ex);
//                        answer = descript.split(">")[0].substring(0, 20);
//                    }
//                }
                // 转换时间
                dataMapper.insertArticle(element.getTitle(), answer, element.getLink(), element.getAuthor(), time);
            }
        }
    }



    //  ！！！以下是Rss源的相关操作！！！
    public void insertFeed(String name, String url) {
        dataMapper.insertFeed(name, url);
        parseFeed(url);
    }

    public ArrayList<RssFeed> getAllFeeds() {
        return dataMapper.getAllFeeds();
    }

    public void updateFeed(RssFeed rssfeed) {
        dataMapper.updateFeed(rssfeed);
    }

    public void deleteFeedById(int id) {
        dataMapper.deleteFeedById(id);
    }


    //  ！！！以下是文章的相关操作！！！
    public void insertArticle(String title, String description, String url, String author, String time){
        dataMapper.insertArticle(title, description, url, author, time);
    }

    public ArrayList<Article> getAllArticlesByTime(){ return dataMapper.getAllArticlesByTime();}

//
//    /**
//     * 获取用户数据并调用mapper层上传数据库
//     *
//     * @param request
//     * @param response
//     * @param str
//     * @return
//     */
//    public String Add(HttpServletRequest request, HttpServletResponse response, String str) {
//
//        getIP getIP = new getIP();
//        getProvince getProvince = new getProvince();
//        getTime getTime = new getTime();
//
//        // 获取信息的IP地址
//        String ip = getIP.get_IP(request, response);
//        // 获取信息所属省份
//        String province = getProvince.get_Province(ip);
//        // 获取当前时间
//        String time = getTime.get_Time();
//        // 设置点赞数为0
//        int likes = 0;
//
//        // 上传数据
//        dataMapper.insert(ip, province, time, str, likes);
//
//        return "succeed";
//    }
//
//    /**
//     * 查询点赞数排名前50的信息
//     *
//     * @return
//     */
//    public List<getUser> findByLikes() {
//        return dataMapper.findByLikes();
//    }
//
//    /**
//     * 查询最新的50条信息
//     *
//     * @return
//     */
//    public List<getUser> findByTime() {
//        return dataMapper.findByTime();
//    }
//
//    /**
//     * 查询随机的50条信息
//     *
//     * @return
//     */
//    public List<getUser> findByRand() {
//        return dataMapper.findByRand();
//    }
//
//    /**
//     * 更新指定id对应的点赞数+1
//     *
//     * @param id
//     * @return
//     */
//    public String increaseLikesById(int id) {
//        dataMapper.increaseLikesById(id);
//
//        return "succeed";
//    }
//
//    /**
//     * 更新指定id对应的点赞数-1
//     *
//     * @param id
//     * @return
//     */
//    public String decreaseLikesById(int id) {
//        dataMapper.decreaseLikesById(id);
//
//        return "succeed";
//    }
}

