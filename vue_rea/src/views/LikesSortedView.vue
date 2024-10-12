<template>
  <div class="LikesSorted">
    <UserList
      v-for="(item, index) in Obj"
      :key="index"
      :Obj="item"
    />
      <!-- 触发对话框的按钮 -->
      <el-button type="primary" @click="dialogVisible = true">添加RSS源</el-button>
    <!-- 弹出的对话框 -->
    <el-dialog
      title="新增RSS源"
      :visible.sync="dialogVisible"
      width="30%">
      <el-form >
        <el-form-item label="名称">
          <el-input v-model="inputName" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="种子">
          <el-input v-model="inputUrl" placeholder="请输入种子"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// @ 是 /src 的别名
import UserList from '@/components/UserList.vue'
import axios from 'axios'


export default {
  name: 'LikesSortedView',
  components: {
    UserList
  },

  data() {
    return {
      Obj: [],
      dialogVisible: false,
      inputName: "",
      inputUrl: ""
    }
  },

  // computed会缓存结果，methods每次都会重新计算
  methods: {
    getList() {
      let list = [];
      let newObjects = {};

      axios.get('http://localhost:8087/getAllRss')
        .then(res => {
          list = res.data;

          for (let i = 0; i < list.length; i++) {
            newObjects[i] = list[i];
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
    
    handleSubmit(){
      const encodedUrl = encodeURIComponent(this.inputUrl);
      console.log(this.inputName,this.inputUrl);
      axios.put(`http://localhost:8087/Add/${this.inputName}/${encodedUrl}`);
    }

  },



  created() {
    this.getList();
  }

}
</script>
