package com.example.rss_spring.controller;

import com.example.rss_spring.model.collectArticle;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import com.example.rss_spring.model.Article;
import com.example.rss_spring.model.RssFeed;
import com.example.rss_spring.service.RAG;
//import com.example.rss_spring.rssHandler.rssParser;
import com.example.rss_spring.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;

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


    @ApiOperation("得到所有收藏的文章")
    @GetMapping("/getCollectArticles")
    public ArrayList<collectArticle> getCollectArticles() {
        return dataService.getAllArticlesByCollect();
    }


    @ApiOperation("上传信息接口")
    @PutMapping("/Add/{name}/{url}")
    public void addRss(@PathVariable String name, @PathVariable String url) {
        String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);
        dataService.insertFeed(name,decodedUrl);
    }

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

    @ApiOperation("查看某一篇文章的LLM摘要")
    @GetMapping("/getArticleLLMById")
    public String getArticleLLMById(@RequestParam int id) {
        return dataService.getArticleLLMById(id);
    }

    @ApiOperation("通过收藏的PDF ID索取pdf文件")
    @GetMapping("/getPdfById")
    public ResponseEntity<Resource> getPdfById(@RequestParam int id, @RequestParam String name) throws MalformedURLException, FileNotFoundException {
        try {
            String pdfPath = dataService.getPdfPath();
            String upLoads = dataService.getUpLoadsPath();
            String filePath = pdfPath + id + ".pdf";
            File file = new File(filePath);
            if (!file.exists()) {
                filePath= upLoads+name+".pdf";
            }
            System.out.println(filePath);
            Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()) {
                throw new FileNotFoundException("File not found");
            }
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.getFilename());
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation("上传PDF文件")
    @RequestMapping("/uploadFile")
    public collectArticle uploadFile(@RequestParam("file") MultipartFile file) {
        return DataService.upLoadPDF(file);
    }


    @CrossOrigin(origins = "http://localhost:8086")
    @ApiOperation("向RAG提出问题")
    @PostMapping("/RAGquery")
    public String RAGquery(@RequestParam String query) throws Exception {
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        return RAG.queryHaystack(query);
    }

}

