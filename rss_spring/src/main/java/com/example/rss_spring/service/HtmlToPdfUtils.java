package com.example.rss_spring.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.font.FontProvider;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.micrometer.common.util.StringUtils;

// 将html文本转换为pdf文件
public class HtmlToPdfUtils {

    public static void convertToPdf(String html, String outputPath) throws IOException {
        // 下载远程图片并替换URL为本地路径
        html = downloadImagesAndReplacePaths(html);

        InputStream inputStream = new ByteArrayInputStream(html.getBytes());
        OutputStream outputStream = new FileOutputStream(outputPath);

        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        // 添加中文字体支持
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();

        String fontPath = "src/main/resources/font/msyh.ttc,0";
        if (StringUtils.isNotBlank(fontPath)) {
            PdfFont microsoft = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, false);
            fontProvider.addFont(microsoft.getFontProgram(), PdfEncodings.IDENTITY_H);
        }
        properties.setFontProvider(fontProvider);

        // 生成pdf文档
        HtmlConverter.convertToPdf(inputStream, pdfDocument, properties);
        pdfWriter.close();
        pdfDocument.close();
    }

    private static String downloadImagesAndReplacePaths(String html) {
        // 定义正则表达式匹配 <img> 标签
        String imgTagPattern = "<img\\s+[^>]*src=['\"]([^'\"]+)['\"][^>]*>";

        // 使用正则表达式替换所有 <img> 标签为空字符串
        return html.replaceAll(imgTagPattern, "");
//        Pattern imgTagPattern = Pattern.compile("<img[^>]*src=\"(http[^\"]+)\"");
//        Matcher matcher = imgTagPattern.matcher(html);
//
//        String localImageDir = "images/";
//        File dir = new File(localImageDir);
//        if (!dir.exists()) dir.mkdirs();
//
//        while (matcher.find()) {
//            String imageUrl = matcher.group(1);
//            try {
//                // 下载图片并存储在本地
//                String localImagePath = localImageDir + Paths.get(new URL(imageUrl).getPath()).getFileName();
//                System.out.println(localImagePath);
//                try (InputStream in = new URL(imageUrl).openStream()) {
//                    Files.copy(in, Paths.get(localImagePath));
//                }
//                // 将HTML内容中的图片路径替换为本地路径
//                //html = html.replace(imageUrl, ("/src/main/resources/images/"+Paths.get(new URL(imageUrl).getPath()).getFileName()));
//                html = html.replace(imageUrl, new File(localImagePath).toURI().toString());
//            } catch (IOException e) {
//                System.out.println("Failed to download image: " + imageUrl);
//                e.printStackTrace();
//            }
//        }
//        return html;
    }
}
