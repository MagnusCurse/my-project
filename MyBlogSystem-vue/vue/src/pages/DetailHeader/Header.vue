<script>

import axios from "axios";
import {mixin} from "@/mixin";

export default {
  name: "Header",
  data() {
    return {
      activeName: 'second'
    };
  },
  mixins: [mixin],
  methods: {
    likeBlog() {
      // 发送请求给后端
      axios({
        url: "http://localhost:9090/blog-liked/like",
        method: "get",
        params: {
          likedBlogId: this.getURLParam("id")
        }
      }).then(function (response) {
         if(response.data.code == 200) {
           if(response.data.val == 1) {
             console.log("点赞成功");
           } else {
             console.log("取消点赞成功");
           }
         } else {
           alert("点赞/取消点赞失败,请重试");
         }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    }
  }
}
</script>

<template>
<div class="header">
    <div class="buttons">
      <button @click="likeBlog" class="button is-primary">
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
.header {
  margin-bottom: 6px;
}
.buttons {
  float: right;
}
hr {
  height: 1px; /* 设置分割线的高度 */
  background-color: rgb(255, 255, 255); /* 设置分割线的颜色 */
  border: none; /* 移除默认的边框 */
  margin: 10px 0; /* 调整分割线的上下边距 */
}
</style>