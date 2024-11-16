package com.example.rss_spring.service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class HtmlText {
    public static String extractTextFromHtml(String htmlContent) throws IOException {
        // 解析HTML字符串
        Document document = Jsoup.parse(htmlContent);

        // 删除非文本元素，例如图片、脚本和样式等
        document.select("script, style, img, meta, link").forEach(Element::remove);

        // 提取纯文本并清理多余的空行
        String rawText = document.text();
        String cleanText = rawText.replaceAll("\\s+", " ").trim();

        return cleanText;
    }

    public static void main(String[] args) {

    }
}

