<template>
  <div class="Newest">
    <div class="wrapper" v-for="(item, index) in Obj" :key="index">
      <div class="container">
        <!-- 使用 transition 组件添加切换动画 -->
        <div @click="handleClick(index)">
        <transition name="scale" mode="out-in">
          <component
            :is="itemVisibility[index] ? 'ArticleList' : 'ContentList'"
            :Obj="item"
          />
        </transition>
        </div>
        <transition name="slide" mode="out-in"> <!-- 添加 transition 组件 -->
          <div class="button-container" v-if="!itemVisibility[index]">
              <button class="styled-button" @click="openOriginal(item)" >跳转原文</button>
              <button :class="itemCollect[index]==1 ? 'styled-button-another':'styled-button'"  @click="collectItem(item,index)" >收藏</button>
              <button class="styled-button" @click="collapseContent(index)">收起</button>
          </div>
        </transition>
      </div>
    </div>
  </div>
</template>

<style>
.slide-enter-active {
  transition: transform 1.5s ease, opacity 1.5s ease; /* 设置持续时间为 2s */
}
.slide-enter, .slide-leave-to {
  transform: translateY(30px); /* 向下移动 30 像素 */
  opacity: 0; /* 初始透明度为 0 */
}
.scale-enter-active {
  transition: transform 1s ease, opacity 1s ease; /* 将持续时间设置为 1s */
}
.scale-enter {
  transform: scale(0); /* 缩放至 0 */
  opacity: 0; /* 透明度设为 0 */
}
.scale-leave-to {
  transform: scale(0); /* 缩放至 0 */
  opacity: 0; /* 透明度设为 0 */
}

.wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 使内容靠左对齐 */
  margin: auto; /* 确保整体居中并有间隔 */
  width: 100%;
}
.container {
  position: relative; /* 设置为相对定位 */
  display: flex; /* 使用flex布局 */
  flex-direction: row; /* 子元素横向排列 */
  align-items: flex-start; /* 对齐方式，确保子元素从顶部开始 */
  margin:auto;
}

  .button-container {
  position: absolute;
  display: flex;
  flex-direction: column; /* 设置为纵向排列 */
  justify-content: flex-end; /* 按钮向右对齐 */
  gap: 30px; /* 按钮间距 */
  width: 150px; /* 让按钮容器占满宽度 */
  top: 70px; /* 适当调整顶部位置 */
  right: -70px; /* 适当调整右侧位置 */
}
  
  .styled-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px; /* 圆角 */
  background: linear-gradient(135deg, #6e7efc, #4a90e2); /* 渐变背景 */
  color: white; /* 文字颜色 */
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.2s ease; /* 过渡效果 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 阴影效果 */
}

  .styled-button-another {
  padding: 10px 20px;
  border: none;
  border-radius: 5px; /* 圆角 */
  background: linear-gradient(135deg, #ee4c62, #f01154); /* 渐变背景 */
  color: rgb(255, 255, 255); /* 文字颜色 */
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.2s ease; /* 过渡效果 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 阴影效果 */
}

.styled-button:hover {
  background: linear-gradient(135deg, #4a90e2, #6e7efc); /* 悬停时的渐变背景 */
  transform: translateY(-2px); /* 悬停时微微上升 */
}

.styled-button:active {
  transform: translateY(2px); /* 点击时下沉 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* 点击时减少阴影 */
}
</style>

  <script>
  // @ 是 /src 的别名
  import ArticleList from '@/components/ArticleList.vue'
  import ContentList from '@/components/ContentList.vue'
  import axios from 'axios'
  
  export default {
    name: 'ArticleView',
    components: {
      ArticleList,
      ContentList
    },
  
    data() {
      return {
        Obj: [],
        itemVisibility: [],
        itemCollect: []
      }
    },
  
    // computed会缓存结果，methods每次都会重新计算
    methods: {
      getList() {
        let list = [];
        let newObjects = {};
  
        axios.get('http://localhost:8087/getAllArticlesByTime')
          .then(res => {
            list = res.data;
  
            for (let i = 0; i < list.length; i++) {
              newObjects[i] = list[i];
              this.itemVisibility[i] = true;
              this.itemCollect[i]=list[i].collect;
            }
            console.log(newObjects);
            this.Obj = newObjects;
          })
          .catch(error => {
            this.$message({
              message: '获取页面内容失败！',
              type: 'error',
              duration: 2000
            });
            console.log("获取排行出错！")
            console.error(error);
          });
      },
      trans(index) {
        this.$set(this.itemVisibility, index, !this.itemVisibility[index]);
      },

      handleClick(index) {
        console.log('Clicked index:', index);
        // 当 ArticleList 被点击时，更新 currentView 为 'user'
        // if(this.itemVisibility[index]){
        //   this.itemVisibility[index] = false;
        // }
        // else{
        //   this.itemVisibility[index] = true;
        // }
        if(this.itemVisibility[index]){
          this.$set(this.itemVisibility, index, !this.itemVisibility[index]);
        }
      },
      handleCollapse(index) {
      // 收起 ContentList，切换回 ArticleList
      this.$set(this.itemVisibility, index, true);
      console.log("shouqi");
    },
    openOriginal(item) {
      // 使用 URL 打开新选项卡
      window.open(item.url, '_blank');
    },
    collapseContent(index) {
      this.$set(this.itemVisibility, index, true);
    },

    collectItem(item, index){
      if(this.itemCollect[index]==1){
        console.log("取消收藏");
        this.$set(this.itemCollect,index , 0);
        console.log("取消收藏成功");
      }
      else{
        console.log("收藏");
        this.$set(this.itemCollect,index, 1);
      }
      axios
        .get(`http://localhost:8087/updateArticleCollect`,{
          params: {
            id: item.id,
            collect: this.itemCollect[index] // 假设你有一个collect属性
          }
        })
        .then()
        .catch(error => {
          this.$message({
            message: '收藏设置失败！',
            type: 'error',
            duration: 2000
          });
          console.error("收藏设置出错！", error);
        });
    }
    },
    created() {
      this.getList();
    },

  
  }
  </script>
  