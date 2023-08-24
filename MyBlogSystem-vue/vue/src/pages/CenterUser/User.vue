<script xmlns:v-bind="http://www.w3.org/1999/xhtml">
import axios from "axios";

export default {
  name: "User",
  data() {
    return {
      fileInput: {},
      imageUrl: "", // 当前用户头像的 url
      avatarFile: null, // 头像的文件对象
      nickname: "未填写",
      email: "未填写",
      introduction: "未填写"
    }
  },
  methods: {
    chooseFile() {
      if(confirm("是否更换当前头像")) {
        this.$refs.fileInput.click();
      }
    },
    handleFileChange(event) {
      // event.target 表示触发事件的 DOM 元素
      // files 属性包含了用户选择的文件列表
      // files[0] 表示选择的第一个文件
      const file = event.target.files[0];
      if(file) { // 已选择文件
        this.avatarFile = file; // 将上传的文件保存
        // 创建 FileReader 对象,用于读取文件内容
        const reader = new FileReader();
        // 这是一个回调函数，它会在文件读取完成后被调用。在这个函数内部，可以处理读取到的文件内容
        reader.onload = (response) => {
          // response.target.result 包含了读取到的文件内容，通常是一个 Data URL，表示文件的 Base64 编码字符串
          this.imageUrl = response.target.result; // 更新 imageUrl 的值
        }
        // 读取文件并将其编码为 Data URL
        reader.readAsDataURL(file);
        this.upLoadAvatar(); // 调用上传头像函数
      }
    },
    // 上传头像函数
    upLoadAvatar() {
      if(this.avatarFile) {
        // this.avatarFile 应该是一个文件对象，而不是直接传递给后端的数据。正确的方式是使用 FormData 来处理文件上传
        const formData = new FormData();
        formData.append("file", this.avatarFile);

        // 发送请求给后端
        // 直接将 formData 传递给后端即可,不需要使用 data: {}
        axios.post("http://localhost:9090/user/upload-avatar",formData).
        then(function (response) {
           if(response.data.code == 200 && response.data.val == 1) {
             alert("更新头像成功");
           } else {
             alert("修改头像失败,请重试");
           }
        }).catch(function (error) {
          console.log(error);
          alert("出现异常,详情见控制台");
        })
      }
    }
  },
  mounted() {


  }
}
</script>

<template>
  
  <div class="account-wrapper" style="--delay: .8s">
    <div class="account-profile">
      <div class="avatar">
        <!--  input 框用来存储文件对象     -->
        <input type="file" ref="fileInput" @change="handleFileChange" style="display:none;" >
        <img :src="imageUrl" @click="chooseFile" alt="">
      </div>
      <div class="blob-wrap">
        <div class="blob"></div>
        <div class="blob"></div>
        <div class="blob"></div>
      </div>
      <div class="account-name">Mike J Morgan</div>
      <div class="account-title">Taxi Driver</div>
    </div>
    <!-- 用户信息卡  -->
    <div class="account card">
      <div class="account-cash"></div>
      <div class="account-info">
         <div style="display: flex">
           <div class="nickname">昵称: {{ nickname }}</div>
           |
           <div class="email">邮箱: {{ email }}</div>
         </div>
         <div class="introduction">简介: {{ introduction }}</div>
         <el-button style="float: right" type="info" icon="el-icon-edit-outline" size="small" circle></el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

.account {
  width: 100%;
  height: 180px;
  margin-top: auto;
  flex-grow: 0;
  position: relative;
  transition: 0.3s;
  cursor: pointer;

}
.account:hover {
  transform: scale(1.02);
}
.account:before {
  content: "";
  position: absolute;
  width: 24px;
  height: 24px;
  box-shadow: -15px 0 0 0 #ef8741;
  background: #ef415c;
  top: 20px;
  left: 42px;
  border-radius: 50%;
}
.account-wrapper {
  width: 100%;
  height: 30%;
  display: flex;
  margin-left: 20px;
  align-items: center;
}
.account-profile {
  margin: auto;
  position: relative;
  text-align: center;
  width: 20%;

}
.account-profile img {
  width: 84px;
  height: 84px;
  border-radius: 50%;
  object-fit: cover;
  object-position: left;
  border: 3px solid #4255d3;
  padding: 5px;
}
.account-profile .blob {
  position: absolute;
  border-radius: 50%;
  animation: fly 5.8s linear infinite alternate;
}
.account-profile .blob:nth-child(1) {
  width: 14px;
  height: 14px;
  top: 25px;
  left: -20px;
  background: #28327a;
  animation-delay: 0.9s;
}
.account-profile .blob:nth-child(2) {
  width: 18px;
  height: 18px;
  background: #87344c;
  right: -20px;
  top: -20px;
  animation-delay: 0.2s;
}
.account-profile .blob:nth-child(3) {
  width: 12px;
  height: 12px;
  background: #13645b;
  right: -35px;
  top: 50%;
  animation-delay: 1.8s;
}
.account-name {
  margin: 20px 0 10px;
  color: #9b9ca7;
}
.account-title {
  font-size: 14px;
  color: #9b9ca7;
}
.account-cash {
  font-size: 22px;
  font-weight: 500;
  margin-bottom: 6px;
  padding-top: 16px;
  position: relative;
}
.account-cash:before {
  position: absolute;
  width: 5px;
  height: 5px;
  background: #9b9ca7;
  right: 10px;
  border-radius: 50%;
  box-shadow: -10px 0 0 0 #9b9ca7, 10px 0 0 0 #9b9ca7;
  top: 24px;
}

.account-info {
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  border-radius: 5px;
  height: 100%;
  color: white;
}
.nickname {
  margin-right: 10px;
}

.email {
  margin-right: 10px;
  margin-left: 10px;
}

.card {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  padding: 40px 30px;
  border-radius: 6px;
  width: 70%;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
}

.card + .card {
  margin-left: 20px;
}


</style>