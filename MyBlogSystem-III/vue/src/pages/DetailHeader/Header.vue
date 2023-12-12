<script>

import axios from "axios";
import {commonMixin, mixin} from "@/mixin";

export default {
  name: "Header",
  data() {
    return {
      likeCount: 0,
      viewCount: 0
    };
  },
  methods: {
    // 点赞 / 取消点赞该博客
    likeBlog() {
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "blog-liked/like",
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
           originThis.initLikeCount();
         } else {
           alert("点赞/取消点赞失败,请重试");
         }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 初始化该博客点赞数量
    initLikeCount() {
      // 发送请求给后端
      const originThis = this; // 缓存 this
      axios({
        url: "blog-liked/init-like-count",
        method: "get",
        params: {
          likedBlogId: this.getURLParam("id")
        }
      }).then(function (response) {
        if(response.data.code == 200 && response.data.val != null) {
           originThis.likeCount = response.data.val;
        } else if(response.data.val == null) {
           originThis.likeCount = 0;
        } else {
           alert("初始化点赞数失败");
        }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 浏览量 + 1
    viewBlog() {
      // 发送请求给后端
      const originThis = this; // 缓存 this
      axios({
        url: "blog/view",
        method: "get",
        params: {
          blogId: this.getURLParam("id")
        }
      }).then(function (response) {

      }).catch(function (error) {
        console.log(error); alert("出现异常,详情见控制台");
      })
    },
    // 初始化博客浏览量
    initBlogViews() {
      // 发送请求给后端
      const originThis = this; // 缓存 this
      axios({
        url: "blog/init-views",
        method: "get",
        params: {
          blogId: this.getURLParam("id")
        }
      }).then(function (response) {
          if(response.data.code === 200 && response.data.val != null) {
            originThis.viewCount = response.data.val;
          }
      }).catch(function (error) {
        console.log(error); alert("出现异常,详情见控制台");
      })
    }
  },
  mixins: [commonMixin],
  mounted() {
    // 初始化点赞数量
    this.initLikeCount();
    // 增加博客浏览量
    this.viewBlog();
    // 初始化浏览量
    this.initBlogViews();

  }
}
</script>

<template>
<div class="header">
    <div class="buttons">
      <button @click="likeBlog" class="button is-primary">
        <i class="fa-regular fa-heart fa-lg" style="color: #eca1df;"></i>
        <strong style="margin-left: 6px"> Like </strong>
        <span  style="margin-left: 12px"> {{ likeCount }} </span>
      </button>
      <button class="button is-light">
        <i class="fa-regular fa-eye fa-lg" style="color: #b7c1d1;"></i>
        <span style="margin-left: 6px">View</span>
        <span style="margin-left: 12px"> {{ viewCount }} </span>
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