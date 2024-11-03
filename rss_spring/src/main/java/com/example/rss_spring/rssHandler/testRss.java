package com.example.rss_spring.rssHandler;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.zhipu.oapi.ClientV4;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class testRss {



    public static void main(String[] args) throws Exception{
        //String url = "https://www.ruanyifeng.com/blog/atom.xml";
        String url = "https://www.zhihu.com/rss";
        SyndFeed parseFeed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        //System.out.println(parseFeed);
        List list = parseFeed.getEntries();
        SyndEntry feedEntry = (SyndEntry)list.get(0);
        System.out.println(feedEntry.getPublishedDate());

        // 设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 转换时间
        System.out.println( simpleDateFormat.format(feedEntry.getPublishedDate()));

        //System.out.println(feedEntry.getAuthor());
        //System.out.println(feedEntry.getLink());
        //System.out.println(feedEntry);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(feedEntry.getDescription().getValue());  // 阮一峰的引言
        //System.out.println(feedEntry.getTitle());
        //String s = feedEntry.getContents().get(0).getValue();
        //System.out.println(feedEntry.getContents());
//        String API_SECRET_KEY = "45082cddbe7f6b83ed08be8cf4bbf652.HtMW5Hw4nJddXRmV";
//        ClientV4 client = new ClientV4.Builder(API_SECRET_KEY)
//                .enableTokenCache()
//                .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
//                .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
//                .build();


    }
}
