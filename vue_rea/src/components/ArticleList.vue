<template>
  <div class="ArticleList">
    <div class="box">
      <div class="box1">
        <div class="str">{{ title }}</div>
      </div>
      <div class="box2">
        <div class="province">{{ author }}</div>
        <div class="likes">{{ time }}</div>
        <!-- 
        <img
          :src="imagePath"
          @click="toggleImage"
          class="img-btn"
        />
        -->
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.ArticleList {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.box {
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 2px 2px 4px #ddd;
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 5px;
  width: 100%;
  max-width: 500px;
  transition: transform 0.3s ease;
}

.box:hover {
  transform: scale(1.04);
}

.box1 {
  width: 100%;
  margin: 5px 0;
}

.str {
  flex: 1;
  text-align: center;
  font-size: 15px;
  width: 100%;
  height: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
}

.box2 {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.province, .likes {
  font-size: 10px;
  height: 20px;
  display: flex;
  align-items: center;
}

.province {
  width: 120px;
  justify-content: center;
}

.likes {
  width: 100px;
  justify-content: flex-end;
}

.img-btn {
  width: 16px;
  height: 16px;
  transition: transform 0.3s ease;
}
</style>

<script>
export default {
  name: 'ArticleList',
  props: {
    Obj: {
      type: Object,
      required: true
    }
  },
  computed: {
    title() {
      return this.Obj.title;
    },
    author() {
      return this.Obj.author;
    },
    time() {
      return this.Obj.time.split(" ")[0];
    }
  },
  created() {
    // if (localStorage.getItem(`isLiked${this.Obj.id}`)) {
    //   this.imagePath = require("@/assets/点赞-yes.png");
    // }
  },
  methods: {
    // emitClickItem() {
    //   console.log("Article clicked");
    //   this.$emit('clickItem');
    // }
    // toggleImage() {
    //   // 判断是否点赞过
    //   if (this.imagePath === require("@/assets/点赞-no.png")) {
    //     const imgBtn = this.$el.querySelector('.img-btn');
    //     imgBtn.style.transform = 'scale(1.2) rotate(-10deg)';
    //     setTimeout(() => {
    //       imgBtn.style.transform = 'scale(1) rotate(10deg)';
    //       setTimeout(() => {
    //         imgBtn.style.transform = 'scale(1) rotate(0deg)';
    //       }, 300);
    //     }, 300);
    //     this.likes++;
    //     this.imagePath = require("@/assets/点赞-yes.png");
    //     localStorage.setItem(`isLiked${this.Obj.id}`, true);
    //     axios.put(`http://localhost:8087/increaseLikesById/${this.Obj.id}`)
    //       .then(res => {
    //         console.log(res.data);
    //       })
    //       .catch(error => {
    //         console.log("点赞出错！");
    //         console.error(error);
    //       });
    //   } else {
    //     this.likes--;
    //     this.imagePath = require("@/assets/点赞-no.png");
    //     localStorage.removeItem(`isLiked${this.Obj.id}`);
    //     axios.put(`http://localhost:8087/decreaseLikesById/${this.Obj.id}`)
    //       .then(res => {
    //         console.log(res.data);
    //       })
    //       .catch(error => {
    //         console.log("取消点赞出错！");
    //         console.error(error);
    //       });
    //   }
    // }
  }
}
</script>