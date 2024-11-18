<template>
  <div class="PdfList" @click="handleClick">
    <div class="box" :class="{ 'choose-mode': isChooseMode }">
      <div class="name">{{ name }}</div>
    </div>
  </div>
</template>

<!-- 添加“scoped作用域”属性以将 CSS 限制为此组件 -->
<style scoped lang="scss">
.PdfList {
  display: flex;
  justify-content: center;
  align-items: center;
}

.box {
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 2px 2px 4px #ddd;
  padding: 10px;
  display: flex;
  flex-direction: column; // 将 flex 子元素沿竖直方向排列
  align-items: center; // 将 flex 子元素在纵轴上居中对齐
  margin: 5px;
  width: 100px;
  transition: transform 0.3s ease; // 优化过渡效果，仅针对缩放
}

.box:hover {
  transform: scale(1.04); // 面板放大
}

.name {
  flex: 1;
  text-align: center;
  font-size: 10px;
  // font-family: wpyt;
  width: 100%;
  height: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
}

.box.choose-mode {
  border-color: rgb(0, 234, 255);
  background-color: #ffe6e6;
}

/* 响应式设计 */
@media (max-width: 400px) {
  .PdfList {
    flex-direction: column;
  }

  .box {
    width: 80px;
    padding: 8px;
  }

  .name {
    font-size: 8px;
    height: 20px;
  }
}
</style>

<script>
export default {
  name: 'PdfList',
  props: {
    Obj: {
      type: Object,
      required: true
    },
    isChooseMode: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    name() {
      return this.Obj.name;
    },
    id() {
      return this.Obj.id;
    }
  },
  methods: {
    handleClick() {
      this.$emit('displayit', this.Obj);
    }
  }
}
</script>