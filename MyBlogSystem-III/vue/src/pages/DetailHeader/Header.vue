<script>

import axios from "axios";
import {commonMixin, mixin} from "@/mixin";

export default {
  name: "Header",
  data() {
    return {
      likeCount: 0,
      viewCount: 0,
      userImgUrl: "",
      username: "",
      // 当前博客用户 id
      userId: "",
      // 判断该博客用户是否已经被当前用户关注
      followed: false
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
    },
    // 初始化当前博客的用户对象
    initBlogUser() {
      // 发送请求给后端
      const originThis = this; // 缓存 this
      axios({
        url: "blog/init-blog-user",
        method: "get",
        params: {
          blogId: this.getURLParam("id")
        }
      }).then(function (response) {
        if(response.data.code === 200 && response.data.val != null) {
          const user = response.data.val;
          originThis.userImgUrl = "/img/avatar/" + user.avatarUrl;
          originThis.username = user.username;
          originThis.userId = user.id;
          // 初始化 followed，初始化完 user.id 后即可初始化 followed
          originThis.isFollow();
        }
      }).catch(function (error) {
        console.log(error); alert("出现异常,详情见控制台");
      })
    },
    // 判断当前博客用户是否已经被当前用户关注
    isFollow() {
      // 发送请求给后端
      const originThis = this; // 缓存 this
      axios({
        url: "follow/is-follow",
        method: "get",
        params: {
          followUserId: originThis.userId
        }
      }).then(function (response) {
        if(response.data.code === 200 && response.data.val != null) {
           originThis.followed = response.data.val;
        }
      }).catch(function (error) {
        console.log(error); alert("出现异常,详情见控制台");
      })
    },
    // 关注当前用户
    followUser() {
      // 发送请求给后端
      const originThis = this; // 缓存 this
      axios({
        url: "follow/user",
        method: "get",
        params: {
          followUserId: originThis.userId,
          isFollow: originThis.followed
        }
      }).then(function (response) {
        if(response.data.code === 200 && response.data.val == 1) {
           alert("关注成功");
           // 刷新当前页面

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
    // 初始化当前博客用户对象
    this.initBlogUser();
  }
}
</script>

<template>
<div class="header">
    <div class="user-box">
      <img class="user-img" :src="userImgUrl" alt="">
      <br>
      <span class="nickname" style="color: white; margin-left: 18px"> {{ username }} </span>
    </div>
    <div class="buttons">
      <button class="custom-btn btn-8">
        <span> Follow </span>
      </button>
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
  display: flex;
  justify-content: space-between;

}
.buttons {
  float: right;
}

button {
  width: 130px;
  height: 40px;
  margin: 6px;
}
hr {
  height: 1px; /* 设置分割线的高度 */
  background-color: rgb(255, 255, 255); /* 设置分割线的颜色 */
  border: none; /* 移除默认的边框 */
  margin: 10px 0; /* 调整分割线的上下边距 */
}

.user-box {

}

.user-box img {
  width: 84px;
  height: 84px;
  border-radius: 50%;
  object-fit: cover;
  object-position: left;
  border: 3px solid #4255d3;
  padding: 5px;
  margin-top: 6px;
}

.custom-btn {
  width: 130px;
  height: 40px;
  color: #fff;
  border-radius: 5px;
  padding: 10px 25px;
  font-family: 'Lato', sans-serif;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
  7px 7px 20px 0px rgba(0,0,0,.1),
  4px 4px 5px 0px rgba(0,0,0,.1);
  outline: none;
}

.btn-8 {
  background-color: #f0ecfc;
  background-image: linear-gradient(315deg, #f0ecfc 0%, #c797eb 74%);
  line-height: 42px;
  padding: 0;
  border: none;
}
.btn-8 span {
  position: relative;
  display: block;
  width: 100%;
  height: 100%;
}
.btn-8:before,
.btn-8:after {
  position: absolute;
  content: "";
  right: 0;
  bottom: 0;
  background: #c797eb;
  /*box-shadow:  4px 4px 6px 0 rgba(255,255,255,.5),
              -4px -4px 6px 0 rgba(116, 125, 136, .2),
    inset -4px -4px 6px 0 rgba(255,255,255,.5),
    inset 4px 4px 6px 0 rgba(116, 125, 136, .3);*/
  transition: all 0.3s ease;
}
.btn-8:before{
  height: 0%;
  width: 2px;
}
.btn-8:after {
  width: 0%;
  height: 2px;
}
.btn-8:hover:before {
  height: 100%;
}
.btn-8:hover:after {
  width: 100%;
}
.btn-8:hover{
  background: transparent;
}
.btn-8 span:hover{
  color: #c797eb;
}
.btn-8 span:before,
.btn-8 span:after {
  position: absolute;
  content: "";
  left: 0;
  top: 0;
  background: #c797eb;
  /*box-shadow:  4px 4px 6px 0 rgba(255,255,255,.5),
              -4px -4px 6px 0 rgba(116, 125, 136, .2),
    inset -4px -4px 6px 0 rgba(255,255,255,.5),
    inset 4px 4px 6px 0 rgba(116, 125, 136, .3);*/
  transition: all 0.3s ease;
}
.btn-8 span:before {
  width: 2px;
  height: 0%;
}
.btn-8 span:after {
  height: 2px;
  width: 0%;
}
.btn-8 span:hover:before {
  height: 100%;
}
.btn-8 span:hover:after {
  width: 100%;
}


</style>