<script>


import axios from "axios";
import {mixin} from "@/mixin";

export default {
  name: "User",
  data() {
    return {
      imageUrl: ""
    }
  },
  methods: {

  },
  mixins: [mixin],
  mounted() {
    this.initAvatar();
  }
}
</script>

<template>
  <div class="account-wrapper" style="--delay: .8s">
    <div class="account-profile">
      <img :src="imageUrl" alt="">
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
  max-width: 310px;
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-left: 20px;
  align-items: center;
}
.account-profile {
  margin: auto;
  position: relative;
  text-align: center;

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
  content: "";
  position: absolute;
  width: 5px;
  height: 5px;
  background: #9b9ca7;
  right: 10px;
  border-radius: 50%;
  box-shadow: -10px 0 0 0 #9b9ca7, 10px 0 0 0 #9b9ca7;
  top: 24px;
}
.account-income {
  font-size: 14px;
}
.account-iban {
  margin-top: auto;
  font-weight: 500;
}

.card {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  padding: 40px 30px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  max-height: 460px;
  width: 90%;
}

.card + .card {
  margin-left: 20px;
}


</style>