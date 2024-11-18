package com.example.rss_spring.service;

import com.example.rss_spring.mapper.DataMapper;
import com.example.rss_spring.model.Article;
import com.example.rss_spring.model.RssFeed;

import com.example.rss_spring.model.collectArticle;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Service
public class DataService {
    @Autowired(required = false)
    private DataMapper dataMapper;


//    @Data
    private ArrayList<RssFeed> feedArray;
//
//    @Data
    private ArrayList<Article> articleArray;


    private String pdfPath = "saveDocs/Articles/";
    private static String upLoads = "saveDocs/upLoads/";

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
            System.out.println("订阅失败");
            return;
        }
        List<SyndEntry> list = parseFeed.getEntries();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //遍历这个rss源的所有文章
        for (SyndEntry element : list) {
            String time = simpleDateFormat.format(element.getPublishedDate());
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
                String s = "null";
                try{
                    s = element.getContents().get(0).getValue();
                } catch (Exception e) {
                    s = element.getDescription().getValue();
                }

                // 转换时间
                dataMapper.insertArticle(element.getTitle(), answer, element.getLink(), element.getAuthor(), time, s, 0);
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
    public void insertArticle(String title, String description, String url, String author, String time, String content){
        dataMapper.insertArticle(title, description, url, author, time, content, 0);
    }

    public ArrayList<Article> getAllArticlesByTime(){ return dataMapper.getAllArticlesByTime();}

    public String getContentById(int id){ return dataMapper.getContentById(id);}

    public void updateArticleCollect(int id, int collect){
        File dir = new File(pdfPath);
        if (!dir.exists()) dir.mkdirs();
        String filePath = pdfPath + id + ".pdf";
        if(collect==1){  // 收藏 并下载保存为pdf
            try{
                HtmlToPdfUtils.convertToPdf(getContentById(id), filePath);
            }catch (Exception e){
                return;
            }
        }
        else{
            File file = new File(filePath);
            if(file.delete()){
                System.out.println("文件删除成功！");
            }
        }
        dataMapper.updateArticleCollect(id,collect);
    }

    public static collectArticle upLoadPDF(MultipartFile file){
        try {
            // 设置保存文件的路径
            File uploadDir = new File(upLoads);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 创建目录
            }
            // 保存文件到指定目录
            String filePath = upLoads + file.getOriginalFilename();
            System.out.println(filePath);
            File dest = new File(filePath);
            InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(dest);

            byte[] buffer = new byte[4096];
            int bytesRead;

            // 循环读取和写入
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            String realname=file.getOriginalFilename();
            assert realname != null;
            return new collectArticle(-1, realname.replaceAll("\\.pdf$",""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPdfPath(){
        return this.pdfPath;
    }

    public String getUpLoadsPath(){
        return upLoads;
    }


    public int getArticleCollectById(int id){ return dataMapper.getArticleCollectById(id);}

    public ArrayList<collectArticle> getAllArticlesByCollect(){
        ArrayList<collectArticle> result=new ArrayList<>();
        File uploadDir = new File(upLoads);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 创建目录
        }
        File[] files = uploadDir.listFiles(); // 获取文件夹中的文件和子目录
        if (files != null) {
            for (File file : files) {
                result.add(new collectArticle(-1, file.getName().replaceAll("\\.pdf$","")));
                System.out.println(file.getName());
            }
        }
        for(Article webarticle: dataMapper.getALLArticleByCollect()){
            result.add(new collectArticle(webarticle.getId(), webarticle.getTitle()));
        }
        return result;
    }

    // 生成文章的大模型摘要
    public void updateArticleLLM(int id) throws Exception {
        dataMapper.updateArticleLLM(id, ChatLLm.summarizeHTML(getContentById(id)));
    }

    // 得到文章的大模型摘要
    public String getArticleLLMById(int id) {
        String llm = dataMapper.getArticleLLMById(id);
        if(llm==null){
            try{
                updateArticleLLM(id);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
            llm = dataMapper.getArticleLLMById(id);
        }
        return dataMapper.getArticleLLMById(id);
    }


}

