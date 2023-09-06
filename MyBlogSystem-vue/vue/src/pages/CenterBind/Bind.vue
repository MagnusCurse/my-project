<script>

import axios from "axios";

export default {
  name: "Bind",
  data() {
    return {
      // 初始化六个输入框,可以根据需求调整数量
      inputs: Array(6).fill(""),
    }
  },
  computed: {
    verificationCode() {
      return this.inputs.join("");
    }
  },
  methods: {
    // 操作输入框光标
    handleInput(index) {
      if(index < this.inputs.length - 1) {
        // 如果当前输入框不是最后一个
        if(this.inputs[index].length === 1) {
          // 如果当前输入框已经输入一个字符
          this.$refs.inputs[index + 1].focus(); // 将焦点移动到下一个输入框
        }
      }
    },
    // 通过验证码绑定邮箱
    bindEmail() {
      console.log("调用了 bind")
      if(this.verificationCode.length != 6) {
        alert("请输入完整的验证码");
        return;
      }
      // 发送请求给后端
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "http://localhost:9090/mail/bind",
        method: "get",
        params: {
          code: originThis.verificationCode
        }
      }).then(function (response) {
        if(response.data.code == 200 && response.data.val == 1) {
          alert("绑定邮箱成功");
          originThis.$router.push("/center"); // 跳转回个人中心界面
        } else if(response.data.code == -2) {
          alert("验证码或者待验证邮箱已经过期");
        } else {
          alert("数据库更新失败");
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
<div class="bind">
  <div class="verification-code">
    <label class="control-label">Verification Code</label>
    <div class="verification-code--inputs">
      <input v-for="(input,index) in inputs"
      :key="index"
      type="text"
      maxlength="1"
      @input="handleInput(index)"
      ref="inputs"
      v-model="inputs[index]">
    </div>
    <b-button @click="bindEmail" type="is-warning" style="margin-top: 10px">确认</b-button>
  </div>
</div>
</template>

<style scoped>

.bind {
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  border-radius: 5px;
}

.verification-code {
  max-width: 300px;
  position: relative;
  margin:50px auto;
  text-align:center;
  color: white;
}

.control-label{
  display:block;
  margin:40px auto;
  font-weight:900;
}

.verification-code--inputs input[type=text] {
  border: 2px solid #e1e1e1;
  width: 46px;
  height: 46px;
  padding: 10px;
  text-align: center;
  display: inline-block;
  box-sizing:border-box;
  margin: 2px;
}

</style>