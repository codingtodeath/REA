package com.example.rss_spring.mapper;

import com.example.rss_spring.model.RssFeed;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DataMapper {

    // ！！！以下为rss源的数据库操作方法！！！
    /**
     *
     * @param name       rss源名称
     * @param url        rss源地址
     */

    // 为什么这里是int
    @Insert("INSERT INTO feed (id, name, url) VALUES (#(id), #{name}, #{url})")
    int insertFeed(@Param("id") int id,
                @Param("name") String name,
               @Param("url") String url);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "url", column = "url"),
    })
    @Select("SELECT * FROM feed")
    List<RssFeed> getAllFeeds();

    @Update("UPDATE feed SET name = #{name}, url = #{url} WHERE id= #{id}")
    void updateFeed(RssFeed rssfeed);

    @Delete("DELETE FROM feed WHERE id = #{id}")
    void deleteFeedById(int id);


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


