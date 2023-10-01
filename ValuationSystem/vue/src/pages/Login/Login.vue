<script>
import axios from "axios";

export default {
  name: "Login",
  data() {
    return {
      radio: "",
      disabled: false, // 发送短信按钮
      codeBtnMsg: "发送验证码", // 发送短信按钮提示
      form: {}
    }
  },
  methods: {
    login(){
      // if(!this.radio){
      //   this.$message.error("请先确认阅读用户协议！");
      //   return
      // }
      if(!this.form.phone || !this.form.code){
        this.$message.error("手机号和验证码不能为空！");
        return
      }
      axios.post("/user/phone-login", this.form)
          .then(({data}) => {
            if(data){
              // 保存用户信息到 session
              sessionStorage.setItem("token",data.data);
              console.log("login:" + sessionStorage.getItem("token"));
            }
            // 跳转到个人中心页面
            this.$router.push("/center")
          })
          .catch(err => this.$message.error(err))
    },
    goBack(){
      history.back();
    },
    // 通过手机发送验证码
    sendCode(){
      if (!this.form.phone) {
        this.$message.error("手机号不能为空");
        return;
      }
      // 发送验证码
      axios.post("/user/code?phone="+this.form.phone)
          .then(() => {})
          .catch(err => {
            console.log(err);
            this.$message.error(err)
          });
      // 禁用按钮
      this.disabled = true;
      // 按钮倒计时
      let i = 60;
      this.codeBtnMsg = (i--) + '秒后可重发'
      let taskId = setInterval(() => this.codeBtnMsg = (i--) + '秒后可重发', 1000);
      setTimeout(() => {
        this.disabled = false;
        clearInterval(taskId);
        this.codeBtnMsg = "发送验证码";
      }, 59000)
    }
  }
}
</script>

<template>
  <div class="login-container">
    <div class="header">
      <div class="header-back-btn" @click="goBack" ><i class="el-icon-caret-left"></i></div>
      <div class="header-title" style="font-weight: bold;"></div>
    </div>
    <div class="content">
      <div class="login-form">
        <div style="display: flex; justify-content: space-between">
          <b-field>
            <b-input style="width: 100%" placeholder="请输入手机号" v-model="form.phone"></b-input>
          </b-field>
          <b-button style="width: 38%" @click="sendCode" type="is-success" :disabled="disabled">{{ codeBtnMsg }}</b-button>
        </div>

        <div style="height: 5px"></div>
        <b-input placeholder="请输入验证码" v-model="form.code"></b-input>

        <div style="text-align: center; color: #8c939d;margin: 5px 0">未注册的手机号码验证后自动创建账户</div>
        <b-button @click="login" style="width: 100%;" type="is-warning">登录</b-button>
        <div style="text-align: right; color:#333333; margin: 5px 0"><a href="/login2.html">密码登录</a></div>
      </div>

<!--      <div class="login-radio">-->
<!--        <div>-->
<!--          <input type="radio" name="readed" v-model="radio" value="1">-->
<!--          <label for="readed"></label>-->
<!--        </div>-->
<!--        <div>我已阅读并同意-->
<!--          <a href="javascript:void(0)">-->
<!--            </a>-->
<!--          <a href="javascript:void(0)"></a>-->
<!--        </div>-->
<!--      </div>-->
    </div>
  </div>
</template>

<style scoped>
.login-container{
  height: 100%;
}
.header{
  width: 100%;
  height: 7%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #7957d5;
}
.header-back-btn{
  width: 10%;
  color: #7957d5;
  font-size: 22px;
}
.header-title {
  width: 90%;
  text-align: center;
  font-size: 18px;
  font-family: Hiragino Sans GB,Arial,Helvetica,"\5B8B\4F53",sans-serif;
}

.content{
  height: 93%;
  background-color: #f7f5f5;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.login-form {
  height: 85%;
}
.login-radio {
  height: 15%;
  width: 100%;
  display: flex;
  align-items: start;
}
input[type="radio"] + label::before {
  content: "\a0"; /*不换行空格*/
  display: inline-block;
  vertical-align: middle;
  font-size: 12px;
  width: 0.7em;
  height: 0.7em;
  margin-right: .4em;
  border-radius: 50%;
  border: 2px solid #7957d5;
  text-indent: .15em;
  line-height: 1;
  padding: .2em;
}
input[type="radio"]:checked + label::before {
  background-color: #7957d5;
  background-clip: content-box;
  padding: .2em;
}
input[type="radio"] {
  opacity: 0;
}
</style>