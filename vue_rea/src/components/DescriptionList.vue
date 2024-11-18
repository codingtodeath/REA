<template>
  <div class="summary-box">
    <div v-if="description" class="summary-content">
      <h3>文章摘要</h3>
      <p>{{ description }}</p>
    </div>
    <div v-else class="summary-placeholder">
      <p>正在生成摘要，请稍候...</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "DescriptionList",
  props: {
    description: {
      type: String,
      required: true
    }
  },
  created() {
    console.log("DescriptionList component created");
    // 如果需要获取更多内容，取消注释并实现 fetchDescription
    // if(this.fetchDescription){
    //   this.getContent();
    //   console.log("Fetching additional content...");
    // }
    console.log("DescriptionList initialized");
  },
  methods: {
    // getContent() {
    //   // 实现获取额外内容的逻辑
    // },
  }
};
</script>

<style scoped lang="scss">
/* 摘要框整体样式 */
.summary-box {
  text-align: left; /* 全局文字默认左对齐 */
  flex: 0 0 auto;
  width: 100%; /* 使用百分比宽度提高响应式 */
  max-width: 400px; /* 调整最大宽度 */
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin: 20px auto;
  overflow-y: auto; /* 超出内容自动滚动 */
  font-family: Arial, sans-serif;
  transition: box-shadow 0.3s ease;
}

.summary-box:hover {
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
}

/* 摘要内容样式 */
.summary-content h3 {
  text-align: center;
  margin-bottom: 10px;
  font-size: 18px;
  color: #333;
  font-weight: bold;
}

.summary-content p {
  font-size: 16px;
  color: #555;
  line-height: 1.6;
}

/* 占位内容样式 */
.summary-placeholder {
  font-size: 16px;
  color: #999;
  line-height: 1.6;
  text-align: center; /* 居中等待内容 */
  animation: fade 1.5s infinite; /* 添加淡入淡出的等待动画 */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%; /* 填充整个容器 */
}

/* 淡入淡出动画 */
@keyframes fade {
  0%, 100% {
    opacity: 0.5;
  }
  50% {
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 600px) {
  .summary-box {
    width: 90%;
    padding: 15px;
  }

  .summary-content h3 {
    font-size: 16px;
  }

  .summary-content p {
    font-size: 14px;
  }

  .summary-placeholder {
    font-size: 14px;
  }
}
</style>