<script>
import axios from "axios";
import Header from "@/components/Header/Header.vue";

export default {
  name: "Login",
  data() {
    return {
      radio: "",
      disabled: false, // 发送短信按钮
      codeBtnMsg: "Send code", // 发送短信按钮提示
      form: {}
    }
  },
  components: {
    Header
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
          .then(({data}) => {
            console.log("当前验证码为:" + data.data);
            alert("当前验证码为:" + data.data);
          })
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
    <Header/>
    <div class="content">
      <div class="login-form">
        <div style="display: flex; justify-content: space-between">
          <b-field>
            <b-input style="width: 100%" placeholder="Phone number" v-model="form.phone"></b-input>
          </b-field>
          <b-button style="width: 38%" @click="sendCode" type="is-dark" :disabled="disabled">{{ codeBtnMsg }}</b-button>
        </div>

        <div style="height: 5px"></div>
        <b-input placeholder="Verification Code" v-model="form.code"></b-input>

        <div style="text-align: center; color: #cdd9e5;margin: 5px 0"> An account will be created after an unregistered mobile number verified </div>
        <b-button @click="login" style="width: 100%;" type="is-dark"> Login </b-button>
      </div>

<!-- note 确认协议功能 -->
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

.content{
  height: 93%;
  background-color: #545d68;
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