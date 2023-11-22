<script>
import axios from "axios";
import Footer from "@/pages/HomeFooter/Footer.vue";
import Header from "@/components/Header/Header.vue";

export default {
  name: "Edit",
  components: {
    Footer,
    Header
  },
  data() {
    return {
      // user 包含 icon 和 nickname 信息
      user: "",
      info: {},
      nickname: ""
    }
  },
  methods: {
    modifyNickname(id) {
      if(confirm("是否要修改昵称?")) {
        this.user.nickname = prompt("请输入要修改的昵称");
        if(this.user.nickname === "") {
           alert("当前昵称不能为空");
           return;
        }
        axios.get("/user/info/nickname/" + id, {
          params: {
            nickname: this.user.nickName
          }
        })
        .then(({data}) => {
          alert("修改昵称成功,请重新登录");
        })
        .catch(err => {
          console.log(err);
        })
      }
    },
    checkLogin() {
      // 查询用户信息
      axios.get("/user/me")
          .then(({data}) => {
            if(data.data == null) {
              alert("用户未登录,即将跳转到登录页面");
              this.$router.push("/login");
              return;
            }
            this.user = data.data;
          })
          .catch(error => {
            if (error.response && error.response.status === 401) {
              // 重定向到登录页面
              alert("当前用户未登录，即将跳转到登录页面");
              this.$router.push("/login");
            }
          })
    },
    goBack(){
      history.back();
    },
  },
  mounted() {
    this.checkLogin();
  },
}
</script>

<template>
<div class="edit">
  <Header/>
  <div class="edit-container">
    <div class="info-box">
      <div class="info-item">
        <div class="info-label">头像</div>
        <div class="info-btn">
          <img width="35" :src=" user.icon || '/imgs/icons/default-icon.png'" alt="" style="border-radius: 50%">
          <div><i class="el-icon-arrow-right"></i></div>
        </div>
      </div>
      <div class="divider"></div>
      <!--  昵称   -->
      <div class="info-item">
        <div class="info-label">昵称</div>
        <div class="info-btn" @click="modifyNickname(user.id)">
          <div > {{ user.nickName }} </div>
          <div>
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
      </div>

      <div class="divider"></div>
      <div class="info-item">
        <div class="info-label">个人介绍</div>
        <div class="info-btn">
          <div style="overflow: hidden; width: 150px;text-align: right">{{info.introduce || '介绍一下自己'}}</div>
          <div>
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
      </div>
    </div>


  </div>
  <Footer/>
</div>
</template>

<style scoped>
.edit {
  height: 100%;
  width: 100%;
}

.basic-icon img {
  border-radius: 50%;
  width: 100%;
  height: 100%;
}

.basic-info .name{
  overflow: hidden;
  font-weight: bold;
  font-size: 14px;
}
.basic-info span{
  display: inline-block;
  padding: 0 10px;
  background-color: #eeeded;
  margin: 5px 0 10px;
  border-radius: 2px;
}

.edit-container{
  background-color: #f4f4f4;
  height: 100%;
}
.divider {
  height: 1px;
  background-color: #e4e4e4;
}
.info-box {
  margin-bottom: 10px;
  padding: 5px 15px;
  background-color: #fff;
}
.info-item{
  display: flex;
  justify-content: space-between;
  line-height: 40px;
}
.info-btn{
  display: flex;
}
.info-btn div {
  margin-left: 5px;
}

.blog-img img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.blog-liked img {
  width: 16px;
  height: 16px;
}
.blog-comments i {
  font-size: 16px;
}

.follow-info-icon img {
  border-radius: 50%;
  width: 100%;
  height: 100%;
}

.blog-img2 img{
  width: 100%;
  border-radius: 3px;
}

.blog-user-icon img{
  width: 100%;
}

.blog-liked img{
  width: 30%;
  height: 75%;
  margin-right: 2px;
}
</style>