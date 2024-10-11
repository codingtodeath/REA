package com.example.rss_spring.rssHandler;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.text.SimpleDateFormat;

public class testRss {
    public static void main(String[] args) throws Exception{
        String url = "https://www.ruanyifeng.com/blog/atom.xml";
        //String url = "https://www.zhihu.com/rss";
        SyndFeed parseFeed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        //System.out.println(parseFeed);
        List list = parseFeed.getEntries();
        SyndEntry feedEntry = (SyndEntry)list.get(0);
        System.out.println(feedEntry.getPublishedDate());

        // 设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 转换时间
        System.out.println( simpleDateFormat.format(feedEntry.getPublishedDate()));

//        System.out.println(feedEntry.getAuthor());
//        System.out.println(feedEntry.getLink());
//        System.out.println(feedEntry.getDescription().getValue());  // 阮一峰的引言
//        System.out.println(feedEntry.getTitle());
//        System.out.println(feedEntry.getContents().get(0).getValue());
    }
}
