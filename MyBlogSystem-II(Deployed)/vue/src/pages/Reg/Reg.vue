<script>

import axios from "axios";

export default {
  name: "Register",
  data() {
    return {
      username: "",
      password: "",
      confirm_password: "",
    }
  },
  methods: {
    reg() {
      if (this.username === "") {
        alert("请先输入用户名!!");
        return;
      }
      if (this.password === "") {
        alert("请先输入密码!!");
        return;
      }
      if (this.confirm_password === "") {
        alert("请先输入确认密码!!");
        return;
      }
      if (this.password !== this.confirm_password) {
        alert("两次密码输入必须相同!!");
      }

      const originThis = this; // 缓存 this
      // 发送 ajax 请求给后端
      axios({
        url: "user/reg",
        method: "get",
        params: {
          username: this.username,
          password: this.password
        }
      }).then(
          function (response) {
            if (response.data.code == 200 && response.data.val == 1) {
              alert("注册成功,即将跳转到登录页面!!");
              originThis.$router.push("/login");
            } else {
              alert("注册失败,请重试!!");
            }
          },
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    }
  }
}
</script>

<template>
  <div class="login-box">
    <h2>Register</h2>
    <form>
      <div class="user-box">
        <input type="text" name="" required="" v-model="username">
        <label>Username</label>
      </div>
      <div class="user-box">
        <input type="password" name="" required="" v-model="password">
        <label>Password</label>
      </div>
      <div class="user-box">
        <input type="password" name="" required="" v-model="confirm_password">
        <label>Confirm Password</label>
        <a @click="reg">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          Submit
        </a>
      </div>
      <router-link class="list-group-item" to="/login" active-class="active" style="float: right">
        Login
      </router-link>
    </form>
  </div>
</template>

<style scoped>
.login-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, .5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
  border-radius: 10px;
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
  font-size: 22px;
  font-weight: bold;
}

.login-box .user-box {
  position: relative;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}

.login-box .user-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: .5s;
}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #03e9f4;
  font-size: 12px;
}

.login-box form a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #03e9f4;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: .5s;
  margin-top: 40px;
}

.login-box a:hover {
  background: #03e9f4;
  color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 5px #03e9f4,
  0 0 25px #03e9f4,
  0 0 50px #03e9f4,
  0 0 100px #03e9f4;
}

.login-box a span {
  position: absolute;
  display: block;
}

.login-box a span:nth-child(1) {
  top: 0;
  left: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #03e9f4);
  animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
  0% {
    left: -100%;
  }
  50%, 100% {
    left: 100%;
  }
}

.login-box a span:nth-child(2) {
  top: -100%;
  right: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(180deg, transparent, #03e9f4);
  animation: btn-anim2 1s linear infinite;
  animation-delay: .25s
}

@keyframes btn-anim2 {
  0% {
    top: -100%;
  }
  50%, 100% {
    top: 100%;
  }
}

.login-box a span:nth-child(3) {
  bottom: 0;
  right: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(270deg, transparent, #03e9f4);
  animation: btn-anim3 1s linear infinite;
  animation-delay: .5s
}

@keyframes btn-anim3 {
  0% {
    right: -100%;
  }
  50%, 100% {
    right: 100%;
  }
}

.login-box a span:nth-child(4) {
  bottom: -100%;
  left: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(360deg, transparent, #03e9f4);
  animation: btn-anim4 1s linear infinite;
  animation-delay: .75s
}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }
  50%, 100% {
    bottom: 100%;
  }
}
</style>