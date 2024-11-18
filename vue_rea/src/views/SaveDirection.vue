<template>
  <div class="container">
    <!-- 左侧 PDF 文件名列表 -->
    <div class="sidebar">
      <h2>收藏文件</h2>
      <PdfList
        v-for="(item, index) in Obj"
        :key="item.id"
        :Obj="item"
        :isChooseMode="isChooseMode"
        @displayit="choosePdf(item)"
      />
      <!-- 导入文件按钮 -->
      <button @click="triggerFileUpload" class="import-btn">导入文件</button>
      <input 
        type="file" 
        ref="fileInput" 
        @change="handleFileUpload" 
        style="display: none;" 
      />
    </div>

    <!-- 中间 PDF 展示区域 -->
    <div class="pdf-viewer">
      <h2>PDF</h2>
      <div class="display-place">
        <div ref="pdfContainer" class="pdf-container">
          <canvas 
            v-for="(page, index) in pages" 
            :key="index" 
            :ref="'pdfCanvas' + index" 
            class="pdf-canvas">
          </canvas>
        </div>
      </div>
    </div>

    <!-- 右侧 RAG 问答区域 -->
    <div class="rag-qa">
      <h2>RAG 问答</h2>
      <div class="dialog-box">
        <div v-for="(dialog, index) in dialogList" :key="index" class="dialog-item">
          <p><strong>用户：</strong>{{ dialog.question }}</p>
          <p><strong>AI：</strong>{{ dialog.answer }}</p>
        </div>
      </div>
      <div class="input-text">
        <input
          type="text"
          maxlength="500"
          v-model="newQuestion"
          placeholder="What sports does Liao Yefei like?"
        />
        <img src="../assets/send.png" class="submit-btn" @click="askQuestion"/>
      </div>
    </div>
  </div>
</template>
  
<script>
import PdfList from '@/components/PdfList.vue'
import axios from 'axios'
import { getDocument, GlobalWorkerOptions } from 'pdfjs-dist/build/pdf';

export default {
  name: 'SaveDirection',
  components: {
    PdfList
  },

  data() {
    return {
      Obj: [],
      currentPdfUrl: '',
      dialogList: [],
      pages: [],
      newQuestion: '',
      isChooseMode: false,
      displayId: 0
    }
  },

  methods: {
    async getList() {
      try {
        const res = await axios.get('http://localhost:8087/getCollectArticles');
        this.Obj = res.data;
      } catch (error) {
        this.$message({
          message: '获取收藏文章失败！',
          type: 'error',
          duration: 2000
        });
        console.error("获取收藏文章出错！", error);
      }
    },

    showPdf(url) {
      this.currentPdfUrl = url;
    },

    async askQuestion() {
      if (!this.newQuestion.trim()) return;
      const question = this.newQuestion.trim();
      this.dialogList.push({ question, answer: '等待回答...' });
      this.newQuestion = '';

      try {
        const response = await axios.post(`http://localhost:8087/RAGquery?query=${encodeURIComponent(question)}`);
        const answer = response.data;
        this.$set(this.dialogList, this.dialogList.length - 1, { question, answer });
      } catch (error) {
        console.error('发送请求时出错:', error);
        this.$set(this.dialogList, this.dialogList.length - 1, { question, answer: '出错了，请重试。' });
      }
    },

    async choosePdf(item){
      this.displayId = item.id;
      try {
        const response = await axios.get(`http://localhost:8087/getPdfById?id=${item.id}&name=${item.name}`, {
          responseType: 'blob'
        });

        if (response.status === 200) {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          this.currentPdfUrl = url;

          const pdf = await getDocument(url).promise;
          const numPages = pdf.numPages;
          this.pages = Array.from({ length: numPages }, () => null);

          for (let pageNum = 1; pageNum <= numPages; pageNum++) {
            const page = await pdf.getPage(pageNum);
            const viewport = page.getViewport({ scale: 1.5 });
            const pageref = `pdfCanvas${pageNum - 1}`;
            const canvas = this.$refs[pageref][0];
            if (canvas) {
              const context = canvas.getContext('2d');
              canvas.height = viewport.height;
              canvas.width = viewport.width;

              const renderContext = {
                canvasContext: context,
                viewport: viewport
              };
              await page.render(renderContext).promise;
            } else {
              console.error(`Canvas for page ${pageNum} not found.`);
            }
          }
        } else {
          console.error('Failed to fetch PDF:', response.status);
        }
      } catch (error) {
        console.error('Error loading PDF:', error);
      }
    },

    triggerFileUpload() {
      this.$refs.fileInput.click();
    },

    async handleFileUpload(event) {
      const file = event.target.files[0];
      if (!file) return;

      const formData = new FormData();
      formData.append("file", file);

      try {
        const response = await axios.post("http://localhost:8087/uploadFile", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
          timeout: 60000,
        });
        const newFile = response.data;
        this.Obj.push(newFile);
        this.$refs.fileInput.value = null;
        this.$message({
          message: '文件上传成功！',
          type: 'success',
          duration: 2000
        });
      } catch (error) {
        console.error("文件上传失败", error);
        this.$message({
          message: '文件上传失败！',
          type: 'error',
          duration: 2000
        });
      }
    }
  },

  created() {
    this.getList();
  },

  mounted(){
    GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.worker.min.js';
  }
}
</script>

<style scoped lang="scss">
.container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 20%;
  padding: 10px;
  border-right: 1px solid #ccc;
  overflow-y: auto;
  position: relative;

  .import-btn {
    display: block;
    margin-top: 10px;
    padding: 8px 12px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;

    &:hover {
      background-color: #0056b3;
    }
  }
}

.pdf-viewer {
  width: 50%;
  padding: 10px;

  .display-place {
    border: 1px solid #ccc;
    width: 100%;
    height: 100%;
    overflow: auto;
    position: relative;

    .pdf-container {
      display: flex;
      flex-direction: column;
    }

    .pdf-canvas {
      display: block;
      width: 100%;
      height: auto;
      margin-bottom: 10px;
    }
  }
}

.rag-qa {
  width: 30%;
  padding: 10px;
  border-left: 1px solid #ccc;
  display: flex;
  flex-direction: column;
  position: relative;

  .dialog-box {
    flex: 1;
    overflow-y: auto;
    margin-bottom: 50px;

    .dialog-item {
      margin-bottom: 10px;
      padding: 5px;
      background-color: #f5f5f5;
      border-radius: 5px;
    }
  }

  .input-text {
    position: absolute;
    bottom: 20%;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    justify-content: space-between;
    padding: 10px;
    width: 300px;
    z-index: 999;

    input[type="text"] {
      flex: 1;
      height: 40px;
      padding: 0 10px;
      background-color: rgba(255, 255, 255, 0.7);
      border: 1px solid transparent;
      border-radius: 20px;
      box-shadow: 0 0 10px #42b983;
      transition: all 0.3s ease-in-out;

      &:hover {
        transform: scale(1.05);
        box-shadow: 0 0 15px rgba(24, 144, 255, 0.8);
      }
    }

    .submit-btn {
      height: 40px;
      padding: 2px 0 0 10px;
      background-color: transparent;
      border: none;
      cursor: pointer;
      transition: transform 0.3s;

      &:hover {
        transform: scale(1.2);
      }
    }
  }
}
</style>