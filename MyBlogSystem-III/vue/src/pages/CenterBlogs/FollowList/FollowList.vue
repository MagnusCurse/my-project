<script>

import axios from "axios";

export default {
  name: "FollowList",
  data() {
    return {
      // 该用户的关注列表
      followList: [],
      // 建立头像 url 和 user 的映射关系
      imageUrlMap: {
      }
    }
  },
  methods: {
      // 初始化该用户的关注列表
      initFollowList() {
        const originThis = this; // 缓存 this
        // 发送请求给后端
        axios({
          url: "follow/init-follow-list",
          method: "get",
        }).then(function (response) {
          if(response.data.code == 200 && response.data.val != null) {
             originThis.followList = response.data.val;
             originThis.followList.forEach(follow => {
                originThis.$set(originThis.imageUrlsMap,follow.id,"/img/avatar/" + follow.avatarUrl);
             })
           }
        }).catch(function (error) {
          console.log(error);
          alert("出现异常,详情见控制台");
        })
      },
      initImageUrlMap() {
        this.followList.forEach(follow => {
          console.log(follow);
          this.$set(this.imageUrlsMap,follow.id,"/img/avatar/" + follow.avatarUrl);
        })
      }
  },
  mounted() {
     this.initFollowList();
  }
}
</script>

<template>
  <div class="activity card">
    <b-field  style="width: 50%; margin: 18px auto">
      <b-input placeholder="Search..."
               type="search"
               icon-pack="fas"
               icon="search"
               v-model="title"
               @keyup.enter.native="search">
      </b-input>
    </b-field>
    <div class="users" v-for="follow in followList" :key="follow.id">
      <!--  关注用户对象    -->
      <el-card class="user" style="margin: 16px">
        <!--   博客内容部分   -->
        <div class="text item" style="margin-bottom: 10px">
          <!-- 用户头像 -->
          <img class="user-img" src="/img/avatar/Default.png" alt="">
          <!-- 用户昵称 -->
          <span style="font-weight: bold"> {{ follow.username }} </span>
          <br>
          <span>
            <!-- 这边后面填写博客简介: to be continued -->
          </span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>

.activity {
  margin: 5px;
}

.user-img {
  width: 84px;
  height: 84px;
  border-radius: 50%;
  object-fit: cover;
  object-position: left;
  border: 3px solid #4255d3;
  padding: 5px;
  margin-top: 6px;
}
.card {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  padding: 40px 30px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  width: 100%;
}
</style>