<script>

export default {
  name: "Header",
  data() {
    return {
      activeName: 'second'
    };
  },
  methods: {

  }
}
</script>

<template>
<div class="header">
    <div class="buttons">
      <button class="button is-primary">
        <i class="fa-regular fa-heart fa-lg" style="color: #eca1df;"></i>
        <strong style="margin-left: 6px"> Like </strong>
        <span style="margin-left: 12px"> 6k </span>
      </button>
      <button class="button is-light">
        <i class="fa-regular fa-eye fa-lg" style="color: #b7c1d1;"></i>
        <span style="margin-left: 6px">View</span>
        <span style="margin-left: 12px"> 6k </span>
      </button>
    </div>
</div>
</template>

<style scoped>
.buttons {
  margin-bottom: 0px;
}
hr {
  height: 1px; /* 设置分割线的高度 */
  background-color: rgb(255, 255, 255); /* 设置分割线的颜色 */
  border: none; /* 移除默认的边框 */
  margin: 10px 0; /* 调整分割线的上下边距 */
}
</style>