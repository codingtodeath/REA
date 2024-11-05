package com.example.rss_spring.mapper;

import com.example.rss_spring.model.Article;
import com.example.rss_spring.model.RssFeed;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DataMapper {

    // ！！！以下为rss源的数据库操作方法！！！
    // 为什么这里是int
    @Insert("INSERT INTO feed (name, url) VALUES (#{name}, #{url})")
    void insertFeed(
                @Param("name") String name,
               @Param("url") String url);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "url", column = "url"),
    })
    @Select("SELECT * FROM feed")
    ArrayList<RssFeed> getAllFeeds();

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "url", column = "url"),
    })
    @Select("SELECT * FROM feed WHERE id = #{id}")
    RssFeed findFeedById(@Param("id") int id);

    @Update("UPDATE feed SET name = #{name}, url = #{url} WHERE id= #{id}")
    void updateFeed(RssFeed rssfeed);

    @Delete("DELETE FROM feed WHERE id = #{id}")
    void deleteFeedById(int id);


    // ！！！以下为文章的数据库操作方法！！！

    // 插入文章
    @Insert("INSERT INTO article (title, description, url, author, time, content, collect) VALUES (#{title}, #{description}, #{url}, #{author}, #{time}, #{content}, #{collect})")
    void insertArticle(
                   @Param("title") String title,
                   @Param("description") String description,
                   @Param("url") String url,
                      @Param("author") String author,
                      @Param("time") String time,
                    @Param("content") String content,
                   @Param("collect") int collect);

    // 此篇文章是否存在
    @Select("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM article WHERE author = #{author} and time = #{time}")
    boolean isArticleHere(@Param("author") String author,
                          @Param("time") String time);

    // 返回按时间排序的文章
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "url", column = "url"),
            @Result(property = "author", column = "author"),
            @Result(property = "time", column = "time"),
            @Result(property = "collect", column = "collect"),
    })
    @Select("SELECT * FROM article ORDER BY time DESC")
    ArrayList<Article> getAllArticlesByTime();

    // 得到文章内容
    @Select("SELECT content FROM article WHERE id = #{id}")
    String getContentById(int id);

    // 更新文章收藏状态
    @Update("UPDATE article SET collect = #{collect} WHERE id= #{id}")
    void updateArticleCollect(int id, int collect);

    // 查询文章收藏状态
    @Select("SELECT collect FROM article WHERE id = #{id}")
    int getArticleCollectById(int id);

    // 查询全部收藏的文章
    @Select("SELECT * FROM article WHERE collect = 1")
    ArrayList<Article> getALLArticleByCollect();
//    /**
//     * 查询点赞数前50名的信息
//     */
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "province", column = "province"),
//            @Result(property = "str", column = "str"),
//            @Result(property = "likes", column = "likes")
//    })
//    @Select("SELECT * FROM dream ORDER BY likes DESC LIMIT 50")
//    List<getUser> findByLikes();
//
//    /**
//     * 查询最新的50条信息
//     */
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "province", column = "province"),
//            @Result(property = "str", column = "str"),
//            @Result(property = "likes", column = "likes")
//    })
//    @Select("SELECT * FROM dream ORDER BY time DESC LIMIT 50")
//    List<getUser> findByTime();
//
//    /**
//     * 查询随机的50条信息
//     */
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "province", column = "province"),
//            @Result(property = "str", column = "str"),
//            @Result(property = "likes", column = "likes")
//    })
//    @Select("SELECT * FROM dream ORDER BY rand() DESC LIMIT 50")
//    List<getUser> findByRand();
//
//    /**
//     * 更新指定id的点赞数+1
//     */
//    @Update("UPDATE dream SET likes = likes + 1 WHERE id = #{id}")
//    void increaseLikesById(int id);
//
//    /**
//     * 更新指定id的点赞数-1
//     */
//    @Update("UPDATE dream SET likes = likes - 1 WHERE id = #{id}")
//    void decreaseLikesById(int id);
}


