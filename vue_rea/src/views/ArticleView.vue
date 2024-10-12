<template>
  <div class="Newest">
    <div v-for="(item, index) in Obj" :key="index" @click="handleClick(index)">
      <!-- 使用 v-if 结构根据状态显示不同的组件 -->
      <ArticleList
        v-if="itemVisibility[index]"
        :Obj="item"
      />
      <UserList
        v-else
        :Obj="item"
      />
    </div>
  </div>
</template>
  
  <script>
  // @ 是 /src 的别名
  import ArticleList from '@/components/ArticleList.vue'
  import UserList from '@/components/UserList.vue'
  import axios from 'axios'
  
  export default {
    name: 'NewestView',
    components: {
      ArticleList,
      UserList
    },
  
    data() {
      return {
        Obj: [],
        itemVisibility: []
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
      handleClick(index) {
      console.log('Clicked index:', index);
      // 当 ArticleList 被点击时，更新 currentView 为 'user'
      this.itemVisibility[index] = false;
      // 可能还需要传递一些数据给 UserListView
      // this.currentItem = item;
      }
  
    },
  


    created() {
      this.getList();
    }
  
  }
  </script>
  