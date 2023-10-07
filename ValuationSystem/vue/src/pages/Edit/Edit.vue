<script>
import axios from "axios";


export default {
  name: "Center",
  data() {
    return {
      fileList: [], // 图片文件列表
      params: {},
      showDialog: false, //
      shops: [], // 商户信息
      shopName: "", //商户名称
      selectedShop: {}, // 选中的商户
    }
  },
  methods: {
    queryShops() {
      axios.get("/shop/of/name?name=" + this.shopName)
          .then(({data}) => this.shops = data.data)
          .catch(this.$message.error)
    },
    selectShop(s) {
      this.selectedShop = s;
      this.showDialog = false;
    },
    // 发布博客功能
    submitBlog() {
      let {...data} = this.params;
      //
      data.images = this.fileList.join(",");
      data.shopId = this.selectedShop.id;
      axios.post("/blog", data)
          .then(resp => this.$router.push("/home"))
          .catch(this.$message.error)
    },
    openFileDialog() {
      this.$refs.fileInput.click();
    },
    fileSelected() {
      let file = this.$refs.fileInput.files[0];
      let formData = new FormData();
      formData.append("file", file);
      const config = {
        headers: {"Content-Type": "multipart/form-data;boundary=" + new Date().getTime()}
      };
      // 调用上传图片功能
      axios
          .post("/upload/blog", formData, config)
          //
          .then(({data}) => this.fileList.push("imgs" + data.data))
          // .catch(
          //     // console.log(this.$message.error)
          // )
    },
    deletePic(i) {
      axios.get("/upload/blog/delete?name=" + this.fileList[i])
          // 将图片路径从 fileList 中删除即可
          .then(() => this.fileList.splice(i, 1))
          .catch(this.$message.error)
    },
    checkLogin() {
      // 获取 token
      let token = sessionStorage.getItem("token");
      if (!token) {
        this.$router.push("/login")
      }
      // 查询用户信息
      axios.get("/user/me")
          .then()
          .catch(err => {
            this.$message.error(err);
            setTimeout(() => this.$router.push("/login"), 200)
          })
    },
    goBack() {
      history.back();
    },
    handleRemove(file) {
      console.log(file);
    }
  },
  mounted() {
    // 检测当前用户是否登录
    this.checkLogin();
    this.queryShops();
  }
}
</script>

<template>
<div class="center">
  <div class="header">
    <div class="header-cancel-btn" @click="goBack">取消</div>
    <div class="header-title">&nbsp;&nbsp;发笔记</div>
    <div class="header-commit">
      <b-button class="header-commit-btn" type="is-warning" @click="submitBlog">发布</b-button>
    </div>
  </div>
  <div class="upload-box">
    <input type="file" @change="fileSelected" name="file" ref="fileInput" style="display: none">
    <div class="upload-btn" @click="openFileDialog">
      <i class="el-icon-camera"></i>
      <div style="font-size: 12px;line-height: 12px">上传照片</div>
    </div>
    <div class="pic-list">
      <div class="pic-box" v-for="(f,i) in fileList" :key="i">
        <img :src="f" alt="">
        <i class="el-icon-close" @click="deletePic(i)"></i>
      </div>
    </div>
  </div>
  <div class="blog-title">
    <input v-model="params.title" type="text" placeholder="填写标题">
  </div>
  <div class="blog-content">
    <textarea v-model="params.content" placeholder="笔记内容~~"></textarea>
  </div>
  <div class="divider"></div>
  <div class="blog-shop" @click="showDialog=true">
    <div class="shop-left">关联商户</div>
    <div v-if="selectedShop.name">{{selectedShop.name}}</div>
    <div v-else>去选择&nbsp;<i class="el-icon-caret-right"></i></div>
  </div>
  <div class="mask" v-show="showDialog" @click="showDialog=false"></div>
  <transition name="el-zoom-in-bottom">
    <div class="shop-dialog" v-show="showDialog">
      <div class="blog-shop">
        <div class="shop-left">关联商户</div>
      </div>
      <div class="search-bar">
        <div class="city-select">杭州 <i class="el-icon-arrow-down"></i></div>
        <div class="search-input">
          <i class="el-icon-search" @click="queryShops"></i>
          <input v-model="shopName" type="text" placeholder="搜索商户名称">
        </div>
      </div>
      <div class="shop-list">
        <div v-for="s in shops" class="shop-item" @click="selectShop(s)">
          <div class="shop-name">{{s.name}}</div>
          <div>{{s.area}}</div>
        </div>
      </div>
    </div>
  </transition>
</div>
</template>

<style scoped>
.header{
  padding: 15px;
  line-height: 20px;
  display: flex;
  justify-content: space-between;
  text-align: center;
}
.header-cancel-btn{
  font-size: 14px;
  font-weight: bold;
}
.header-commit-btn{
  height: 18px;
  padding: 0 12px;
  border-radius: 15px;
}
.header-title i {
  font-size: 14px;
}
.header-title {
  text-align: center;
  font-size: 18px;
  font-family: Hiragino Sans GB,Arial,Helvetica,"\5B8B\4F53",sans-serif;
}
.upload-box {
  padding: 15px;
  display: flex;
  overflow-x: scroll;
}
.upload-btn {
  width: 70px;
  height: 125px;
  line-height: 40px;
  text-align: center;
  align-items: center;
  border: 1px dashed #3a8ee6;
  border-radius: 5px;
  font-size: 30px;
  color: #82848a;
  margin-right: 10px;
}
.upload-btn i {
  margin-top: 35px;
}

.pic-list {
  display: flex;
  overflow-x: scroll;
  height: 125px;
}
.pic-box{
  width: 100px;
  height: 120px;
  border-radius: 5px;
  margin-right: 2px;
  border: 1px solid #c0ccda;
  position: relative;
}
.pic-box img {
  width: 100px;
  height: 120px;
  border-radius: 5px;
}
.pic-box i {
  position: absolute;
  z-index: 99;
  top: 2px;
  right: 2px;
  color: gray;
}
.blog-title,.blog-content {
  padding: 5px 15px;
}
.blog-title input {
  width: 100%;
  line-height: 30px;
  font-size: 14px;
  border-top: 0;
  border-left: 0;
  border-right: 0;
  border-bottom: 1px solid #e6e6e8;
}
input::placeholder {
  font-weight: bold;
  color: #cccccc;
}
textarea::placeholder {
  color: #cccccc;
}
.blog-content textarea{
  width: 100%;
  height: 310px;
  border: 0;
}
.blog-shop {
  color: #82848a;
  padding: 15px;
  display: flex;
  justify-content: space-between;
}
.shop-left {
  color: #111111;
  font-size: 14px;
  font-weight: bold;
}
.divider {
  background-color: #f4f4f5;
  height: 10px;
}
.end-gray {
  background-color: #f4f4f5;
  height: 100%;
}
.mask {
  height: 100%;
  width: 100%;
  position: fixed;
  z-index: 299;
  top: 0;
  left: 0;
  background: rgba(0,0,0,0.3);
}
.shop-dialog {
  position: absolute;
  z-index: 999;
  bottom: 0;
  height: 500px;
  width: 100%;
  background-color: #fff;
}
.search-bar {
  display: flex;
  padding: 15px;
  line-height: 30px;
  justify-content: space-between;
}
.city-select {
  font-weight: bold;
  font-size: 14px;
  margin-right: 10px;
}
.search-input {
  width: 50%;
  flex-grow: 1;
  background-color: #f1f1f1;
  line-height: 30px;
  align-items: center;
  border-radius: 20px;
  display: flex;
  padding: 0 10px ;
}
.search-input input {
  margin-left: 10px;
  border: 0;
  width: 100%;
  background-color: #f1f1f1;
}
input:focus,textarea:focus {
  outline: none;
}
.shop-list {
  padding: 15px;
  overflow-y: scroll;
  height: 100%;
}
.shop-item {
  border-bottom: 1px solid #eae8e8;
  padding: 8px 0;
}
.shop-name {
  font-size: 14px;
  font-weight: bold;
}
</style>