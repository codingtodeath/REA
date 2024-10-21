### 运行步骤

1. 数据库建表，如下所示
   ![image-article](assets\article.png)
   ![image-feed](assets\feeds.png)
   
2. 数据库修改编码(为了处理emoji存储)
   ```sql
   ALTER DATABASE rea CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
   ALTER TABLE article CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
   ALTER TABLE feed CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
   ```

   