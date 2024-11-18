<template>
  <div class="newest">
    <div class="wrapper" v-for="(item, index) in obj" :key="item.id">
      <div class="container">
        <!-- 使用 transition 组件添加切换动画 -->
        <div @click="handleClick(index)" class="horizontal-container">
          <transition name="scale" mode="out-in">
            <DescriptionList
              v-if="itemDescript[index] && !itemVisibility[index]"
              :description="description[index]"
            />
          </transition>
          <transition name="scale" mode="out-in">
            <component
              :is="itemVisibility[index] ? 'ArticleList' : 'ContentList'"
              :obj="item"
            />
          </transition>
        </div>
        <transition name="slide" mode="out-in">
          <div class="button-container" v-if="!itemVisibility[index]">
            <button class="styled-button" @click="openOriginal(item)">跳转原文</button>
            <button
              :class="itemCollect[index] === 1 ? 'styled-button-another' : 'styled-button'"
              @click="collectItem(item, index)"
            >
              收藏
            </button>
            <button class="styled-button" @click="collapseContent(index)">收起</button>
            <button
              :class="itemDescript[index] === 1 ? 'styled-button-another' : 'styled-button'"
              @click="llmDescription(index)"
            >
              摘要
            </button>
          </div>
        </transition>
      </div>
    </div>
  </div>
</template>

<style scoped>
.newest {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 20px 0;
  width: 80%;
}

.container {
  position: relative;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  margin: 0 200px 20px 180px;
}

.horizontal-container {
  display: flex;
  align-items: flex-start; /* 垂直方向居中对齐 */
  justify-content: flex-start; /* 左对齐 */
  gap: 16px; /* 元素之间的间距 */
  flex-wrap: nowrap; /* 禁止子元素换行 */
}

.horizontal-container > * {
  flex: 1; /* 每个子元素占据相等的空间 */
}

.description-list {
  max-width: 40%; /* 可选：限制 DescriptionList 的宽度 */
}

.component-content {
  max-width: 60%; /* 可选：限制 Component 的宽度 */
}

.button-container {
  position: absolute;
  display: flex;
  flex-direction: column; /* 设置为纵向排列 */
  gap: 30px; /* 按钮间距 */
  width: 150px; /* 让按钮容器占满宽度 */
  top: 70px; /* 适当调整顶部位置 */
  right: -170px; /* 适当调整右侧位置 */
}

.styled-button,
.styled-button-another {
  padding: 10px 20px;
  border: none;
  border-radius: 5px; /* 圆角 */
  color: white; /* 文字颜色 */
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s ease-in-out, transform 0.3s ease-in-out; /* 过渡效果更流畅 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 阴影效果 */
}

.styled-button {
  background: linear-gradient(135deg, #6e7efc, #4a90e2); /* 渐变背景 */
}

.styled-button-another {
  background: linear-gradient(135deg, #ee4c62, #f01154); /* 渐变背景 */
}

.styled-button:hover {
  background: linear-gradient(135deg, #4a90e2, #6e7efc); /* 悬停时的渐变背景 */
  transform: translateY(-2px); /* 悬停时微微上升 */
}

.styled-button:active {
  transform: translateY(2px); /* 点击时下沉 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* 点击时减少阴影 */
}

.scale-enter-active,
.scale-leave-active {
  transition: transform 1s ease, opacity 1s ease;
}

.scale-enter,
.scale-leave-to {
  transform: scale(0);
  opacity: 0;
}

.slide-enter-active {
  transition: transform 1.5s ease, opacity 1.5s ease; /* 设置持续时间为 1.5s */
}

.slide-enter,
.slide-leave-to {
  transform: translateY(30px); /* 向下移动 30 像素 */
  opacity: 0; /* 初始透明度为 0 */
}
</style>

<script>
import ArticleList from '@/components/ArticleList.vue'
import ContentList from '@/components/ContentList.vue'
import DescriptionList from '@/components/DescriptionList.vue'
import axios from 'axios'

export default {
  name: 'ArticleView',
  components: {
    ArticleList,
    ContentList,
    DescriptionList
  },
  data() {
    return {
      obj: [],
      itemVisibility: [],
      itemCollect: [],
      itemDescript: [],
      description: []
    }
  },
  methods: {
    getList() {
      axios.get('http://localhost:8087/getAllArticlesByTime')
        .then(res => {
          this.obj = res.data.map((item, index) => ({
            ...item,
            id: item.id || index
          }))
          this.obj.forEach((_, index) => {
            this.$set(this.itemVisibility, index, true)
            this.$set(this.itemCollect, index, this.obj[index].collect)
            this.$set(this.itemDescript, index, false)
            this.$set(this.description, index, '')
          })
          console.log(this.obj)
        })
        .catch(error => {
          this.$message({
            message: '获取页面内容失败！',
            type: 'error',
            duration: 2000
          })
          console.error("获取排行出错！", error)
        })
    },
    handleClick(index) {
      this.$set(this.itemVisibility, index, !this.itemVisibility[index])
      console.log('Clicked index:', index)
    },
    collapseContent(index) {
      this.$set(this.itemVisibility, index, true)
    },
    openOriginal(item) {
      window.open(item.url, '_blank')
    },
    llmDescription(index) {
      const toggle = !this.itemDescript[index]
      this.$set(this.itemDescript, index, toggle)
      if (toggle) {
        axios.get(`http://localhost:8087/getArticleLLMById?id=${this.obj[index].id}`)
          .then(res => {
            this.$set(this.description, index, res.data)
            console.log("Description:", this.description)
          })
          .catch(error => {
            this.$message({
              message: '获取内容失败！',
              type: 'error',
              duration: 2000
            })
            console.error("获取内容出错！", error)
          })
      }
    },
    collectItem(item, index) {
      const newCollect = this.itemCollect[index] === 1 ? 0 : 1
      this.$set(this.itemCollect, index, newCollect)
      console.log(newCollect ? "收藏" : "取消收藏")
      axios.get('http://localhost:8087/updateArticleCollect', {
        params: {
          id: item.id,
          collect: newCollect
        }
      })
      .catch(error => {
        this.$message({
          message: '收藏设置失败！',
          type: 'error',
          duration: 2000
        })
        console.error("收藏设置出错！", error)
      })
    }
  },
  created() {
    this.getList()
  }
}
</script>