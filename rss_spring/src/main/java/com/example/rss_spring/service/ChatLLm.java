package com.example.rss_spring.service;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;

import lombok.experimental.UtilityClass;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatLLm {
    static String chatEndpoint = "https://api.chatanywhere.tech/v1/chat/completions";
    static String apiKey = "sk-36u3JwCjJMcx7KATrGhhAjjlxpI9oPAGmf9lgGNw4XIwY1TX";

    public static String chat(String txt) {
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
            System.out.println(jsonObject);
            JSONArray choices = jsonObject.getJSONArray("choices");
            System.out.println(choices);
            JSONObject result = (JSONObject)choices.get(0);
            message = result.getJSONObject("message");
        } catch (HttpException e) {
            return "出现了异常";
        } catch (ConvertException e) {
            return "出现了异常";
        }
        return message.getString("content");
    }

    public static void main(String[] args) {
        System.out.println(chat("Hello，一个小浪吴啊"));
    }
}
