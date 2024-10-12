### 运行步骤

1. 数据库建表，如下所示
   ![image-20241012150413853](C:\Users\86173\AppData\Roaming\Typora\typora-user-images\image-20241012150413853.png)
   ![image-20241012153721494](C:\Users\86173\AppData\Roaming\Typora\typora-user-images\image-20241012153721494.png)
   
2. 数据库修改编码(为了处理emoji存储)
   ```sql
   ALTER DATABASE rea CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
   ALTER TABLE article CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
   ALTER TABLE feed CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
   ```

   