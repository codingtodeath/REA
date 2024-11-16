package com.example.rss_spring.service;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;

import lombok.experimental.UtilityClass;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ChatLLm {
    private static final String chatEndpoint = "https://api.chatanywhere.tech/v1/chat/completions";
    private static final String apiKey = "sk-36u3JwCjJMcx7KATrGhhAjjlxpI9oPAGmf9lgGNw4XIwY1TX";

    private static final int MAX_CHUNK_SIZE = 2000;  // 最长分块

    public static String chat(String txt) {;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> dataList = new ArrayList<>();
        dataList.add(new HashMap<String, String>(){{
            put("role", "user");
            put("content", txt);
        }});
        paramMap.put("messages", dataList);
        JSONObject message = null;
        try {
            String body = HttpRequest.post(chatEndpoint)
                    .header("Authorization", apiKey)
                    .header("Content-Type", "application/json")
                    .body(JSON.toJSONString(paramMap))
                    .execute()
                    .body();
            JSONObject jsonObject = JSON.parseObject(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = (JSONObject)choices.get(0);
            message = result.getJSONObject("message");
        } catch (HttpException e) {
            return "出现了异常";
        } catch (ConvertException e) {
            return "出现了异常";
        }
        return message.getString("content");
    }

    // 分块长文本
    private static List<String> chunkText(String text, int maxChunkSize) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(text.length(), start + maxChunkSize);
            // 尽量在句子末尾分块
            if (end < text.length() && text.charAt(end) != '。') {
                end = text.lastIndexOf("。", end) + 1;
                if (end <= start) {
                    end = start + maxChunkSize;
                }
            }
            chunks.add(text.substring(start, end).trim());  // 去掉首尾空格
            start = end;
        }
        return chunks;
    }

    // 使用多线程处理分块摘要
    public static String summarizeHTML(String HTML) throws InterruptedException, ExecutionException, IOException {
        String text = HtmlText.extractTextFromHtml(HTML);  // 提取文本
        List<String> chunks = chunkText(text, MAX_CHUNK_SIZE);  // 分块
        System.out.println("分块数：" + chunks.size());

        // 使用线程池
        ExecutorService executor = Executors.newFixedThreadPool(4); // 4 个线程
        List<Future<String>> futures = new ArrayList<>();

        for (String chunk : chunks) {
            futures.add(executor.submit(() -> {
                try {
                    return chat("请直接为以下内容生成简短摘要：\n\n"+chunk);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ""; // 出现错误时返回空摘要
                }
            }));
        }

        // 等待所有任务完成
        StringBuilder combinedSummary = new StringBuilder();
        for (Future<String> future : futures) {
            combinedSummary.append(future.get()).append(" ");
        }

        executor.shutdown(); // 关闭线程池

        // 对合并后的摘要进行最终整合
        return chat("以下是文章的分段总结内容，请进行概括总结，如需分点说明请进行分点：\n\n"+combinedSummary.toString());
    }

    public static void main(String[] args) {
        String filePath = "C:/Users/86173/Desktop/test.html";
        try {
            // 读取整个文件为字符串
            String content = Files.readString(Paths.get(filePath));
            // 生成摘要
            String summary = summarizeHTML(content);
            System.out.println(summary);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
