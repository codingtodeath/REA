<template>
    <div class="container">
    <!-- 左侧 PDF 文件名列表 -->
    <div class="sidebar">
      <h2>我的文件</h2>
      <ul>
        <li v-for="(file, index) in pdfFiles" :key="index" @click="showPdf(file.url)">
          {{ file.name }}
        </li>
      </ul>
    </div>

    <!-- 中间 PDF 展示区域 -->
    <div class="pdf-viewer">
      <h2>PDF</h2>
      <iframe v-if="currentPdfUrl" :src="currentPdfUrl" frameborder="0"></iframe>
    </div>

    <!-- 右侧 RAG 问答区域 -->
    <div class="rag-qa">
      <h2>RAG 问答</h2>
      <div class="DialogBox">
        <div v-for="(dialog, index) in dialogList" :key="index" class="dialog-item">
          <p><strong>用户：</strong>{{ dialog.question }}</p>
          <p><strong>AI：</strong>{{ dialog.answer }}</p>
        </div>
      </div>
      <div class="InputText">
        <input
          type="text"
          maxlength="500"
          v-model="inputValue"
          placeholder="请问..."
        />
        <img src="../assets/send.png" class="submit-btn"/>
      </div>
    </div>

  </div>
</template>
  
<script>

  import axios from 'axios'
  
  export default {
    name: 'SaveDirection',
    components: {

    },
  
    data() {
      return {
        pdfFiles: [ // 假数据，可从后端获取
        { name: 'PDF1', url: '/path/to/pdf1.pdf' },
        { name: 'PDF2', url: '/path/to/pdf2.pdf' },
        { name: 'PDF3', url: '/path/to/pdf3.pdf' }
      ],
      currentPdfUrl: '', // 当前选中的 PDF URL
      question: '', // 用户输入的问题
      answer: '', // 问答结果
      dialogList: [] // 存储问答信息
      }
    },
  
    // computed会缓存结果，methods每次都会重新计算
    methods: {
      getList() {
        axios.get('http://localhost:8087/getArticles') // 假设这个是获取文章的接口
        .then(res => {
          this.articles = res.data; // 假设返回的数据是一个包含文章对象的数组
        })
        .catch(error => {
          this.$message({
            message: '获取文章失败！',
            type: 'error',
            duration: 2000
          });
          console.error("获取文章出错！", error);
        });
      },
      showPdf(url) {
        this.currentPdfUrl = url;
      },
      askQuestion() {
        // 模拟调用 RAG API 进行问答
        // 你可以替换为实际的 API 请求
      this.answer = `你问的问题是：${this.question}，回答是：...`;
      }  
    },
  
    created() {
      this.getList();
    }
  
  }
</script>

<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: row; /* 调整为从左到右的布局 */
  height: 100vh;
}

.sidebar {
  width: 20%;
  padding: 10px;
  border-right: 1px solid #ccc;
  overflow-y: auto;
}

.pdf-viewer {
  width: 50%;
  padding: 10px;
}

.pdf-viewer iframe {
  width: 100%;
  height: 90vh;
}

.rag-qa {
  width: 30%;
  padding: 10px;
  border-left: 1px solid #ccc;
  justify-content: center; /* 水平居中 */
  display: flex;
  flex-direction: column;
  position: relative; /* 确保子元素可以相对于此定位 */
}

.DialogBox {
  flex: 1; /* 让DialogBox占据剩余空间 */
  overflow-y: auto; /* 如果对话过多，允许滚动 */
  margin-bottom: 50px; /* 给输入框留出空间 */
}

.dialog-item {
  margin-bottom: 10px;
  padding: 5px;
  background-color: #f5f5f5;
  border-radius: 5px;
}

.InputText {
  position: absolute;
  bottom: 20%;
  left: 50%; /* 从左侧开始居中 */
  transform: translateX(-50%); /* 通过 transform 让元素水平居中 */
  right: 0;
  display: flex;
  justify-content: space-between;
  padding: 10px;
  background-color: transparent;
  width: 300px;
  z-index: 999;
}

input[type="text"] {
  flex: 1;
  height: 40px;
  padding: 0 10px;
  background-color: rgba(255, 255, 255, 0.7);
  border: 1px solid transparent;
  border-radius: 20px;
  box-shadow: 0 0 10px #42b983;
  transition: all 0.3s ease-in-out;
}

input[type="text"]:hover {
  transform: scale(1.05);
  box-shadow: 0 0 15px rgba(24, 144, 255, 0.8);
}

.submit-btn {
  height: 40px;
  padding: 2px 0px 0px 10px;
  background-color: transparent;
  border: none;
  cursor: pointer;
  transform: scale(1);
  transition: all 0.3s ease-in-out;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
}

.submit-btn:hover {
  transform: scale(1.2);
}
</style>