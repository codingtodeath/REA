package com.example.rss_spring.service;

import com.example.rss_spring.mapper.DataMapper;
import com.example.rss_spring.model.RssFeed;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class DataService {
    @Autowired(required = false)
    private DataMapper dataMapper;


    //  ！！！以下是Rss源的相关操作！！！
    public String insertFeed(int id, String name, String url) {
        dataMapper.insertFeed(id, name, url);
        return "succeed";
    }

    public List<RssFeed> getAllFeeds() {
        return dataMapper.getAllFeeds();
    }

    public void updateFeed(RssFeed rssfeed) {
        dataMapper.updateFeed(rssfeed);
    }

    public void deleteFeedById(int id) {
        dataMapper.deleteFeedById(id);
    }




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

