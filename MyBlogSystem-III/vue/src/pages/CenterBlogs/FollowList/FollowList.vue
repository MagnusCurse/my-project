<script>

export default {
  name: "FollowList",
  data() {
    return {

    }
  },
  methods: {

  },
  mounted() {

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
    <div class="users">
      <!--  关注用户对象    -->
      <el-card class="user" style="margin: 16px">
        <!--   博客内容部分   -->
        <div class="text item" style="margin-bottom: 10px">
          <!-- 用户头像 -->
          <img class="user-img" src="/img/avatar/Default.png" alt="">
          <!-- 用户昵称 -->
          <span style="font-weight: bold"> Nickname </span>
          <br>
          <span>
            <!-- 这边后面填写博客简介: to be continued -->
          </span>
        </div>
      </el-card>
      <el-card class="user" style="margin: 16px">
        <!--   博客内容部分   -->
        <div class="text item" style="margin-bottom: 10px">
          <!-- 用户头像 -->
          <img class="user-img" src="/img/avatar/Default.png" alt="">
          <!-- 用户昵称 -->
          <span style="font-weight: bold"> Nickname </span>
          <br>
          <span>
            <!-- 这边后面填写博客简介: to be continued -->
          </span>
        </div>
      </el-card>
      <el-card class="user" style="margin: 16px">
        <!--   博客内容部分   -->
        <div class="text item" style="margin-bottom: 10px">
          <!-- 用户头像 -->
          <img class="user-img" src="/img/avatar/Default.png" alt="">
          <!-- 用户昵称 -->
          <span style="font-weight: bold"> Nickname </span>
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