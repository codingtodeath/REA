<template>
    <div class="Random">
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
      <textarea v-model="question" placeholder="输入问题..."></textarea>
      <button @click="askQuestion">提交问题</button>
      <div v-if="answer">
        <h3>回答</h3>
        <p>{{ answer }}</p>
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
      answer: '' // 问答结果
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

<style>
.container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 20%;
  padding: 10px;
  border-left: 1px solid #ccc;
  overflow-y: auto;
}

.pdf-viewer {
  width: 50%;
  padding: 10px;
  iframe {
    width: 100%;
    height: 90vh;
  }
}

.rag-qa {
  width: 30%;
  padding: 10px;
  border-right: 1px solid #ccc;
  textarea {
    width: 100%;
    height: 100px;
  }
}
</style>