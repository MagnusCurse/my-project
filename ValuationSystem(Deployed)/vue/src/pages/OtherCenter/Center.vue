<script>
import Footer from "@/pages/HomeFooter/Footer.vue";
import axios from "axios";

export default {
  name: "Center",
  components: {
    Footer
  },
  props: ['id'],
  data() {
    return {
      user: "",
      loginUser: {},
      activeName: "1",
      info: {},
      blogs: [],
      followed: false, // 是否关注了
      commonFollows: [], // 共同关注
    }
  },
  methods: {
    queryBlogs() {
      axios.get("/blog/of/user", {
        params: {
          id: this.user.id, current: 1
        }
      })
          .then(({data}) => this.blogs = data.data)
          .catch(this.$message.error)
    },
    queryLoginUser() {
      // 查询用户信息
      axios.get("/user/me")
          .then(({data}) => {
            // 保存用户
            this.loginUser = data.data;
          })
          .catch(console.log)
    },
    queryUser() {
      // 查询用户信息
      //
      axios.get("/user/" + this.id)
          .then(({data}) => {
            // 保存用户
            this.user = data.data;
            // 查询用户详情
            this.queryUserInfo();
            // 查询用户笔记
            this.queryBlogs();
            // 是否被关注
            this.isFollowed();
          })
          .catch(console.log)
    },
    goBack() {
      history.back();
    },
    queryUserInfo() {
      axios.get("/user/info/" + this.user.id)
          .then(({data}) => {
            if (!data) {
              return
            }
            // 保存用户详情
            this.info = data.data;
            // 保存到本地
            sessionStorage.setItem("userInfo", JSON.stringify(data.data))
          })
          .catch(err => {
            this.$message.error(err);
          })
    },
    isFollowed(){
      axios.get("/follow/or/not/" + this.user.id)
          .then(({data}) => this.followed = data.data)
          .catch(this.$message.error)
    },
    queryCommonFollow() {
      axios.get("/follow/common/" + this.user.id)
          .then(({data}) => this.commonFollows = data.data)
          .catch(err => {
            this.$message.error(err);
          })
    },
    follow() {
      axios.put("/follow/" + this.user.id + "/" + !this.followed)
          .then(() => {
            this.$message.success(this.followed ? "已取消关注" : "已关注")
            this.followed = !this.followed
          })
          .catch(this.$message.error)
    },
    handleClick(t) {
      if (t.name === '2') {
        this.queryCommonFollow();
      }
    },
    toOtherInfo(id){
      location.href = "/other-info.html?id=" + id
    }
  },
  mounted() {
    this.queryUser();
    this.queryLoginUser();
  },
}
</script>

<template>
<div class="center">
  <div class="header">
    <div class="header-back-btn" @click="goBack"><i class="el-icon-arrow-left"></i></div>
    <div class="header-title">&nbsp;&nbsp;&nbsp;</div>
  </div>
  <div class="basic">
    <div class="basic-icon">
      <img :src="user.icon || '/imgs/icons/default-icon.png'" alt="">
    </div>
    <div class="basic-info">
      <div class="name">{{user.nickName}}</div>
    </div>
    <div class="logout-btn" @click="follow" style="text-align: center">
      {{followed ? "Unsubscribe" : "Subscribe"}}
    </div>
  </div>

  <div class="introduce">
    <!-- 目前还没有用户信息,用 info 即可,info.introduce 会报错  -->
    <span v-if="info"></span>
  </div>

  <div class="content">
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-left: 6px">
      <el-tab-pane label="Blogs" name="1">
        <div v-for="b in blogs" :key="b.id" class="blog-item">
          <div class="blog-img">
            <img :src="b.images.split(',')[0]" alt="">
          </div>
          <div class="blog-info">
            <div class="blog-title" v-html="b.title"></div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Mutual Interest" name="2">
        <div>You all followed the:</div>
        <div class="follow-info" v-for="u in commonFollows" :key="u.id">
          <div class="follow-info-icon" @click="toOtherInfo(u.id)">
            <img :src="u.icon || '/imgs/icons/default-icon.png'" alt="">
          </div>
          <div class="follow-info-name">
            <div class="name">{{u.nickName}}</div>
          </div>
          <div class="follow-info-btn" @click="toOtherInfo(u.id)">
            Homepage
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
  <Footer/>
</div>
</template>

<style scoped>
.header{
  width: 100%;
  line-height: 40px;
  height: 6%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #cdd9e5;
}
.header-back-btn{
  width: 10%;
  color: #cdd9e5;
  font-size: 22px;
}
.header-title {
  width: 90%;
  text-align: center;
  font-size: 18px;
  font-family: Hiragino Sans GB,Arial,Helvetica,"\5B8B\4F53",sans-serif;
}
.basic{
  height: 15%;
  display: flex;
  justify-content: space-between;
  padding: 15px 15px 5px 15px;
}
.basic-icon {
  width: 80px;
  height: 80px;
  padding: 1px;
  background-color: #fff;
  border-radius: 50%;
  box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.07);
}
.basic-icon img {
  border-radius: 50%;
  width: 100%;
  height: 100%;
}

.basic-info {
  width: 54%;
  padding: 8px;
}
.basic-info .name{
  overflow: hidden;
  font-weight: bold;
  font-size: 14px;
  color: #cdd9e5;
}
.basic-info span{
  display: inline-block;
  padding: 0 10px;
  background-color: #eeeded;
  margin: 5px 0 10px;
  border-radius: 2px;
}

.edit-btn{
  width: 90%;
  line-height: 20px;
  border-radius: 12px;
  text-align: center;
  border: #eeeded 1px solid;
  box-shadow: 0 1px 2px 1px rgba(0, 0, 0, 0.04);
}
.logout-btn{
  width: 22%;
  margin-top: 8px;
  height: 20px;
  line-height: 20px;
  color: #cdd9e5;
  padding: 0 2px;
  border-radius: 3px;
  background-color: #545d68;
  box-shadow: 0 1px 2px 1px rgba(0, 0, 0, 0.04);
  display: flex;
  justify-content: center;
}

.introduce{
  padding: 0 15px;
}
.content {
  height: 430px;
  background: #d5d7e2;
}


.info-btn div {
  margin-left: 5px;
}

.blog-item {
  display: flex;
  padding: 10px;
  height: 90px;
  width: 90%;
  box-shadow: 1px 1px 4px 1px rgba(0,0,0,0.1);
  margin-left: 16px;
  margin-bottom: 10px;
  background: #545d68;
}
.blog-img {
  width: 70px;
  height: 70px;
  margin-right: 10px;
}
.blog-img img {
  width: 100%;
  height: 100%;
}
.blog-info {
  width: 50%;
  flex-grow: 1;
}
.blog-title {
  line-height: 20px;
  font-weight: bold;
  color: #cdd9e5;
}
.blog-liked {
  line-height: 16px;
  align-items: center;
}
.blog-liked img {
  width: 16px;
  height: 16px;
}
.blog-comments i {
  font-size: 16px;
}


.follow-info{
  display: flex;
  justify-content: space-between;
  padding: 15px 10px 5px 10px;
  align-items: center;
}
.follow-info-icon {
  width: 15%;
  padding: 1px;
  background-color: #fff;
  border-radius: 50%;
  box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.07);
}
.follow-info-icon img {
  border-radius: 50%;
  width: 100%;
  height: 100%;
}
.follow-info-name{
  width: 56%;
  padding: 8px;
  font-size: 14px;
}
.follow-info-btn{
  width: 30%;
  font-size: 10px;
  line-height: 20px;
  height: 20px;
  border: #545d68 1px solid;
  color: #545d68;
  border-radius: 5px;
  text-align: center;
}

/*达人探店列表*/
.blog-list {
  height: 100%;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  overflow-y: auto;
}
.blog-box{
  width: 90%;
  background-color: #fff;
  margin: 5px 0;
  box-shadow: 0 3px 4px 1px rgba(0, 0, 0, 0.1);
  border-radius: 3px;
}
.blog-img2 img{
  width: 100%;
  border-radius: 3px;
}
.blog-title {
  padding: 2px 10px;
  line-height: 24px;
  width: 92%;
  overflow: hidden;
}
.blog-foot {
  display: flex;
  justify-content: space-between;
  margin: 10px 0 5px 0;
  padding: 0 10px;
}
.blog-user-icon {
  width: 10%;
  margin-right: 3px;
}
.blog-user-name {
  width: 65%;
  overflow: hidden;
}
.blog-user-icon img{
  width: 100%;
}
.blog-liked {
  width: 25%;
  display: flex;
  justify-content: flex-end;
}
.blog-liked img{
  width: 30%;
  height: 75%;
  margin-right: 2px;
}

</style>