<template>
    <div class="container">
    <!-- 左侧 PDF 文件名列表 -->
    <div class="sidebar">
      <h2>收藏文件</h2>
      <PdfList
      v-for="(item, index) in Obj"
      :key="index"
      :Obj="item"
      :isChooseMode="isChooseMode"
      @displayit="choosePdf(item)"
    />
    </div>

    <!-- 中间 PDF 展示区域 -->
    <div class="pdf-viewer">
      <h2>PDF</h2>
      <div class="display-place">
        <div ref="pdfContainer" class="pdf-container">
          <canvas v-for="(page, index) in pages" :key="index" :ref="'pdfCanvas' + index" class="pdf-canvas"></canvas>
        </div>
      </div>
      <!-- <iframe v-if="currentPdfUrl" :src="currentPdfUrl" frameborder="0"></iframe> -->
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
  import PdfList from '@/components/PdfList.vue'
  import axios from 'axios'
  import { getDocument } from 'pdfjs-dist/build/pdf';
  import { GlobalWorkerOptions } from 'pdfjs-dist/build/pdf';
  export default {
    name: 'SaveDirection',
    components: {
      PdfList
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
      dialogList: [], // 存储问答信息
      Obj: [],
      diaplayId: 0,
      pages:[]
      }
    },
  
    // computed会缓存结果，methods每次都会重新计算
    methods: {
      
      getList() {
        let list = [];
        let newObjects = {};

        axios.get('http://localhost:8087/getCollectArticles')
          .then(res => {
            list = res.data;

            for (let i = 0; i < list.length; i++) {
              newObjects[i] = list[i];
            }
            this.Obj = newObjects;
        })
        .catch(error => {
          this.$message({
            message: '获取收藏文章失败！',
            type: 'error',
            duration: 2000
          });
          console.error("获取收藏文章出错！", error);
        });
      },
      showPdf(url) {
        this.currentPdfUrl = url;
      },
      askQuestion() {
        // 模拟调用 RAG API 进行问答
        // 你可以替换为实际的 API 请求
      this.answer = `你问的问题是：${this.question}，回答是：...`;
      },
      async choosePdf(item){
        this.displayId=item.id;
        console.log(item.id);

        try {
            const response = await axios.get('http://localhost:8087/getPdfById', {
                params: { id: item.id },
                responseType: 'blob'
            });

            // 确保 Blob 数据有效
            if (response.status === 200) {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                this.currentPdfUrl = url;  // 将生成的 URL 设置为 pdfUrl
                

                // 使用 pdf.js 渲染 PDF
                const pdf = await getDocument(url).promise;  // 使用 URL 而不是 currentPdfUrl
                const numPages = pdf.numPages; // 获取总页数
                this.pages = new Array(numPages); // 初始化页面数组
                for (let pageNum = 1; pageNum <= numPages; pageNum++) {
                    const page = await pdf.getPage(pageNum); // 获取每一页
                    const scale = 1.5; // 缩放比例
                    const viewport = page.getViewport({ scale });

                    // 通过 ref 获取对应的 canvas
                    const pageref='pdfCanvas' + (pageNum - 1);
                    console.log(pageref);
                    const canvas = this.$refs[pageref][0]; // 确保 refs 有相应的画布
                    if (canvas) { // 检查画布是否存在
                        console.log(canvas);
                        const context = canvas.getContext('2d');
                        canvas.height = viewport.height; // 设置画布高度
                        canvas.width = viewport.width; // 设置画布宽度

                        const renderContext = {
                            canvasContext: context,
                            viewport: viewport
                        };
                        await page.render(renderContext).promise; // 渲染页面
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
      }
    },
  
    created() {
      this.getList();
    },

    mounted(){
      GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.worker.min.js';  // CDN 版本
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
  position: relative;
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

.display-place {
    border: 1px solid #ccc; /* 边框样式 */
    width: 100%; /* 根据需要设置宽度 */
    height: 100%; /* 设置高度 */
    overflow: auto; /* 支持滚动 */
    position: relative; /* 相对定位 */
}

.pdf-container {
    display: flex;
    flex-direction: column; /* 垂直排列 */
}

.pdf-canvas {
  display: block; /* 使画布块级显示 */
  width: 100%; /* 使画布宽度自适应 */
  height: auto; /* 高度自动调整 */
  margin-bottom: 10px; /* 页面间距 */
}
</style>