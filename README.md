## 运行步骤

### 前端

1. 安装node、npm、vue-cli等工具

2. 进入vue_rea文件夹，执行命令`npm install`

3. 接着安装以下插件库：
   ```bash
   npm install pdfjs-dist@2.10.377
   npm install --save-dev @babel/plugin-transform-class-static-block
   npm install --save-dev @babel/plugin-transform-private-methods
   ```

4. 安装完成后执行`npm run serve`启动前端



### 后端

#### 数据库建立

```sql
CREATE DATABASE rea
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
use rea;  # 切换到rea database
CREATE TABLE article (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '文章id',
    title TEXT COMMENT '文章标题',
    description MEDIUMTEXT COMMENT '文章概要',
	url VARCHAR(2048) COMMENT '文章原地址',
    author VARCHAR(255) COMMENT '文章作者',
    time DATETIME COMMENT '文章发布时间',
    content LONGTEXT COMMENT '文章内容',
    collect TINYINT COMMENT '文章是否收藏',
    llm LONGTEXT COMMENT '文章的大模型摘要'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE feed (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'rss源序号',
	name VARCHAR(255) COMMENT 'rss源名称',
    url VARCHAR(2048) COMMENT 'rss源地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

# 数据库修改编码(为了处理emoji存储)
ALTER DATABASE rea CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
ALTER TABLE article CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
ALTER TABLE feed CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ai_ci;
```

<h3 style="text-align: center;">
  article
</h3>

| Field       | Type          | Null | Key  | Default | Extra          |
| ----------- | ------------- | ---- | ---- | ------- | -------------- |
| id          | int           | NO   | PRI  | NULL    | auto_increment |
| title       | text          | YES  |      | NULL    |                |
| description | mediumtext    | YES  |      | NULL    |                |
| url         | varchar(2048) | YES  |      | NULL    |                |
| author      | varchar(255)  | YES  |      | NULL    |                |
| time        | datetime      | YES  |      | NULL    |                |
| content     | longtext      | YES  |      | NULL    |                |
| collect     | tinyint       | YES  |      | NULL    |                |
| llm         | longtext      | YES  |      | NULL    |                |

<h3 style="text-align: center;">
  feed
</h3>

| Field | Type          | Null | Key  | Default | Extra          |
| ----- | ------------- | ---- | ---- | ------- | -------------- |
| id    | int           | NO   | PRI  | NULL    | auto_increment |
| name  | varchar(255)  | NO   |      | NULL    |                |
| url   | varchar(2048) | NO   |      | NULL    |                |

#### RAG运行（需要科学上网）

1. 进入`rss_spring/RAG/`文件夹
2. 打开`config.json.example`文件配置api key，配置完成后去掉后面的.example
3. 创建python 3.10环境，`pip install -r ./requirements.txt`安装依赖库
4. `python RAG.py`运行，出现 **[INFO]** 即成功启动



#### Spring后端运行

1. 使用maven构建工程，下载相关依赖
2. 在`rss_spring\src\main\resources\application.properties`中配置数据库地址、用户名和密码信息 ~~（可以先使用idea工具测试能不能连接）~~
3. 运行RssSpringApplication



## 使用

进入vue构建之后的地址即可使用