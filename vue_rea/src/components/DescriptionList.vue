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
    methods: {
        getContent() {
        
    },
    created() {
        console.log("666666");
        if(this.fetchDescription){
            this.getContent();
            console.log("111");
        }
        console.log("222");
    }
    }
};
</script>
  
<style scoped>
  /* 摘要框整体样式 */
  .summary-box {
    text-align: left; /* 全局文字默认左对齐 */
    flex: 0 0 auto;
    width: 200px; /* 固定宽度 */
    height: auto; /* 固定高度 */
    margin-top: 20px;
    overflow: auto; /* 超出内容自动滚动 */
    padding: 15px;
    border: 1px solid #ddd;
    border-radius: 10px;
    background-color: #f9f9f9;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin-right: auto;
    font-family: Arial, sans-serif;
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
</style>

  