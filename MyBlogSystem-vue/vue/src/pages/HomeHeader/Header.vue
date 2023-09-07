<script>


import axios from "axios";

export default{
  name: "Header",
  data() {
    return {
      time: ""
    }
  },
  methods: {
    // 注销功能
    logout() {
      if(confirm("是否注销??")){
        const originThis = this; // 缓存 this
        // 发送请求给后端
        axios({
          url: "user/logout",
          method: "get"
        }).then(
            function (response) {
              if (response.data.code == 200 && response.data.val == 1) {
                alert("注销成功,即将跳转到登录页面");
                originThis.$router.push("/login");
              } else {
                alert("当前没有用户登录,请登录");
                originThis.$router.push("/login");
              }
            }
        ).catch(function (error) {
          console.log(error);
          alert("出现异常,详情见控制台");
        })
      }
    },
    updateTime() {
      this.time = new Date().toLocaleTimeString()
    }
  },
  mounted() {
    setInterval(this.updateTime, 1000);
  }
}
</script>

<template>
  <div class="header">
    <div class="logo">Rent
      <span class=logo-det>Cr</span></div>
    <router-link to="/home" active-class="active" exact class="header-link" href="#">
      <i class="fa-solid fa-house-chimney" style="color: #ffffff; margin-right: 5px"></i>
      主页
    </router-link>
    <router-link to="/center" active-class="active" class="header-link" href="#">
      <i class="fa-solid fa-user" style="color: #ffffff; margin-right: 5px"></i>
      个人中心
    </router-link>
    <router-link to="/home/create" active-class="active" class="header-link" href="#">
      <i class="fa-solid fa-feather" style="color: #ffffff; margin-right: 5px"></i>
      创作
    </router-link>
    <a @click="logout" class="header-link" href="#">
      <i class="fa-solid fa-right-from-bracket" style="color: #ffffff; margin-right: 5px;"></i>
      注销
    </a>

    <div class="user-info">
      <div id="clock">
        <p class="time">{{ time }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header {
  background: #141834;
  background: radial-gradient(circle, #141834 0%, #13162f 100%);
  box-shadow: 0 16px 12px #0e0e23;
  width: 100%;
  padding: 0 30px;
  animation: top 1s both;
  display: flex;
  align-items: center;
  border-radius: 6px;
  font-size: 15px;
  white-space: nowrap;
  position: sticky;
  top: 0;
  left: 0;
  z-index: 10;
}
.header:before {
  content: "";
  width: 100%;
  height: 25px;
  position: absolute;
  top: -25px;
  left: 0;
  background-color: #0e0e23;
}
.header-link {
  color: #9b9ca7;
  text-decoration: none;
  display: flex;
  align-items: center;
  padding: 20px;
  transition: 0.3s;
  border-bottom: 3px solid transparent;
  transition: 0.3s;
}
.header-link svg {
  width: 20px;
  margin-right: 14px;
}

.header-link.active, .header-link:hover {
  background: #11132c;
  border-bottom: 3px solid #4255d4;
}

.logo {
  padding: 20px 50px 20px 0;
  font-size: 16px;
  color: #e7e8ea;
}
.logo-det {
  background: #4255d4;
  padding: 8px;
  margin-left: -2px;
  border-radius: 50%;
  font-size: 15px;
}

.user-info {
  margin-left: auto;
  display: flex;
  align-items: center;
}
.user-info svg {
  width: 20px;
}
.user-info .profile {
  margin: 0 20px 0 12px;
  width: 18px;
}

.button svg {
  margin-left: 10px;
  width: 16px;
}

/* 计时器样式 */
#clock {
  color: #daf6ff;
  text-shadow: 0 0 20px #0aafe6, 0 0 20px rgba(10, 175, 230, 0);
}
#clock .time {
  letter-spacing: 0.05em;
  font-size: 16px;
  padding: 5px 0;
}
</style>