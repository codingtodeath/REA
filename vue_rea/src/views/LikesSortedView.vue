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
      <el-form :model="myform">
        <el-form-item label="name">
          <el-input v-model="myform.myvalue1" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="feed">
          <el-input v-model="myform.value2" placeholder="请输入种子"></el-input>
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
      dialogVisible: false,
      myform: {
      myvalue1: '', // 对应 name
      value2: ''  // 对应 url
              },
      Obj: []
    };
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
    async handleSubmit() {
      console.log('submit now');
      console.log('Name:', this.myform.myvalue1); // 打印 name
      console.log('URL:', this.myform.value2);  // 打印 url
      try {
        // const data = new URLSearchParams();
        // data.append('name', this.form.value1);
        // data.append('url', this.form.value2);
        console.log('submit now');
      console.log('Name:', this.myform.myvalue1); // 打印 name
      console.log('URL:', this.myform.value2);  // 打印 url
    //     axios.post(`http://localhost:8087/insertFeed`,{
    //       id:0,
    //       name:this.myform.myvalue1,
    //       url:this.myform.value2
    //     }, { headers: {
    //      'Content-Type': 'application/json' }
    // });
    const name = this.myform.myvalue1;
    const url = this.myform.value2;

    const encodedName = encodeURIComponent(name);
    const encodedUrl = encodeURIComponent(url);
      //axios.post(`http://localhost:8087/insertFeed/${encodeURIComponent(this.myform.myvalue1)}/${encodeURIComponent(this.myform.value2)}`);
    axios.post(`http://localhost:8087/insertFeed?name=${encodedName}&url=${encodedUrl}`);
        // 关闭对话框并清空表单
        this.dialogVisible = false;
        this.form.value1 = '';
        this.form.value2 = '';
      } catch (error) {
        console.error('提交失败', error);
      }
    }


  },

  created() {
    this.getList();
  },

}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>