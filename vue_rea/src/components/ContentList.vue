<template>
    <div class="userlist-frame">
      <div v-html="HTMLContent" class="content"></div>
    </div>
</template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'ContentList',
    props: {
      Obj: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        HTMLContent: '' // 初始化为空字符串
      };
    },
    methods: {
        getContent() {
      axios
        .get(`http://localhost:8087/getContentById?id=${this.Obj.id}`)
        .then(res => {
          this.HTMLContent = res.data;
        })
        .catch(error => {
          this.$message({
            message: '获取内容失败！',
            type: 'error',
            duration: 2000
          });
          console.error("获取内容出错！", error);
        });
    }
  },
  created() {
    this.getContent();
  }
    }
  </script>
  
  <style>
  .userlist-frame {
    border: 1px solid #ddd;
    border-radius: 15px; /* 加大圆角，使容器更柔和 */
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 更柔和的阴影 */
    padding: 20px 40px; /* 增加左右内边距 */
    width: 60%; /* 使用百分比宽度使容器自适应屏幕 */
    max-width: 1500px;
    max-height: 800px; /* 设置最大高度 */
    overflow-y: auto; /* 启用垂直滚动条 */
    background-color: #fff8dc; /* 米黄色背景 */
    margin: 20px auto; /* 容器水平居中 */
    transition: all 0.3s ease;
  }
  
  /* 针对 v-html 渲染的内容进行样式定义 */
  .content {
    text-align: left; /* 全局文字默认左对齐 */
    line-height: 1.8; /* 增加行高，提升阅读舒适度 */
    font-size: 18px; /* 稍大字体，增强可读性 */
    color: #444; /* 更柔和的字体颜色 */
    margin: 20px 0; /* 上下间距，避免内容紧贴容器 */
    padding: 0 20px; /* 内容与左右边框的适当间距 */
  }
  
  /* 标题样式 */
  .content h1, .content h2, .content h3 {
    color: #333; /* 标题颜色 */
    margin: 20px 0 15px; /* 增加标题上下的间距 */
    text-align: center; /* 标题居中 */
    font-weight: bold; /* 加粗标题 */
    font-family: 'Arial', sans-serif; /* 使标题字体现代化 */
  }
  
  /* 段落样式 */
  .content p {
    margin: 15px 0; /* 段落上下间距 */
    font-size: 18px; /* 统一字体大小 */
    line-height: 1.7; /* 调整行高 */
  }
  
  /* 图片样式 */
  .content img {
    display: block;
    margin: 20px auto; /* 图片水平居中 */
    max-width: 90%; /* 限制图片最大宽度，避免超出容器 */
    height: auto; /* 保持图片比例 */
    border-radius: 8px; /* 图片圆角 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 更柔和的图片阴影 */
  }
  
  /* 列表样式 */
  .content ul, .content ol {
    padding-left: 40px; /* 列表项缩进 */
    margin: 15px 0; /* 列表上下间距 */
    font-size: 18px; /* 统一字体大小 */
  }
  
  .content ul li, .content ol li {
    margin: 8px 0; /* 列表项之间的间距 */
  }
  
  /* 超链接样式 */
  .content a {
    color: #0066cc; /* 超链接颜色 */
    text-decoration: none; /* 去除下划线 */
    font-weight: bold; /* 加粗超链接 */
  }
  
  .content a:hover {
    text-decoration: underline; /* 鼠标悬浮时添加下划线 */
    color: #004a99; /* 悬浮时颜色更深 */
  }
  
  /* 强调文本样式 */
  .content strong {
    font-weight: bold;
    color: #000;
  }
  
  .content em {
    font-style: italic;
    color: #888;
  }
  </style>