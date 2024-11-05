<template>
    <div class="userlist-frame">
      <div v-html="HTMLContent"></div>
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
  
  <style scoped>
  .userlist-frame {
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 2px 2px 4px #ddd;
    padding: 10px;
    width: 1500px;
    max-height: 800px; /* 设置最大高度 */
    overflow-y: auto; /* 启用垂直滚动条 */
    transition: all 0.3s ease;
    background-color: #fff8dc; /* 米黄色背景 */
    margin-bottom: 20px; /* 设置每个 ContentList 间的间隔 */

    margin: 20px auto;
  }
  

</style>
  