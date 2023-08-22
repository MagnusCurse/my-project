<script>
export default {
  name: "User",
  data() {
    return {
      nickname: "未填写",
      email: "未填写",
      introduction: "未填写"
    }
  },
  methods: {

  }
}
</script>

<template>
  <div class="account-wrapper" style="--delay: .8s">
    <div class="account-profile">
      <img src="https://images.unsplash.com/photo-1550314124-301ca0b773ae?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=2215&q=80" alt="">
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