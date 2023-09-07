<script>

import axios from "axios";

export default {
  name: "Edit",
  data() {
    return {
      nickname: "",
      mail: "",
      introduction: ""
    }
  },
  methods: {
    changeNickname() {
      if(this.nickname.length >= 16) {
        alert("昵称长度要小于 16 个字符");
        return;
      }
      // 发送请求给后端
      axios({
        url: "user/change-nickname",
        method: "get",
        params: {
          nickname: this.nickname
        }
      }).then(function (response) {
        if(response.data.code == 200 && response.data.val == 1) {
          alert("修改昵称成功");
          window.location.reload();
        } else {
          alert("修改失败,请稍后重试");
        }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    changeIntroduction() {
      // 发送请求给后端
      axios({
        url: "user/change-introduction",
        method: "get",
        params: {
          introduction: this.introduction
        }
      }).then(function (response) {
        if(response.data.code == 200 && response.data.val == 1) {
          alert("修改简介成功");
          window.location.reload();
        } else {
          alert("修改失败,请稍后重试");
        }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    bindEmail() {
      if(this.email == "") {
        alert("请先输入要绑定的邮箱");
        return;
      }
      if(this.email.length > 256) {
        alert("邮箱长度过长");
        return;
      }
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "mail/send",
        method: "get",
        params: {
          mail: this.mail
        }
      }).then(function (response) {
        if(response.data.code == 200 && response.data.val == 1) {
          alert("已经将验证码发送至您的邮箱");
          originThis.$router.push("/center/bind"); // 跳转到输入验证码页面
        } else {
          alert("邮件格式不正确,请重新输入");
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
<div class="edit">
  <div style="display: flex">
    <div class="form-item">昵称
      <b-field class="editInput">
        <b-input v-model="nickname"  placeholder="请输入昵称" rounded></b-input>
      </b-field>
      <b-button @click="changeNickname" class="editButton" size="is-small" type="is-warning">Edit</b-button>
    </div>
    <div class="form-item">邮箱
      <b-field class="editInput">
        <b-input v-model="mail" placeholder="Email"
                 type="email"
                 icon-pack="fas"
                 icon="envelope">
        </b-input>
      </b-field>
      <b-button @click="bindEmail" class="editButton" size="is-small" type="is-warning">Edit</b-button>
    </div>
  </div>
  <div class="form-item" style="width: 40%">简介
    <b-field>
      <b-input v-model="introduction"  class="editInput" type="textarea"
               minlength="10"
               maxlength="50"
               placeholder="Maxlength automatically counts characters">
      </b-input>
      <b-button @click="changeIntroduction" style="margin-left: 46px" size="is-small" type="is-warning">Edit</b-button>
    </b-field>
  </div>

</div>
</template>

<style scoped>
.edit {
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  border-radius: 5px;
}

.form-item {
  font-weight: bold;
  color: white;
  margin: 16px;
  display: flex;
}

.editInput {
  margin-left: 15px;
}

.editButton {
  margin-left: 15px;
}
</style>