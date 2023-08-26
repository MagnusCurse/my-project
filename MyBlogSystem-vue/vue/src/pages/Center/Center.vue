<script>
import Header from "@/pages/HomeHeader/Header.vue";
import LeftSide from "@/pages/HomeLeftSide/LeftSide.vue";
import User from "@/pages/CenterUser/User.vue";
import Blogs from "@/pages/CenterBlogs/Blogs.vue";

export default {
  name: "Center",
  components: {
    Header,
    LeftSide,
    Blogs,
    User
  },
  computed: {
    isCenterPage() {
      return this.$route.path === "/center"
    }
  }
}
</script>

<template>
  <div id="home">
    <div class="wrapper">
      <!-- 左侧边栏杆  -->
      <LeftSide/>
      <div class="main-container">
        <!--  顶部栏    -->
        <Header/>
        <div class="user-box first-box">
          <!-- 用户区域 -->
          <User/>
        </div>
        <!--  个人中心内容区域,默认显示个人中心博客列表    -->
        <Blogs v-if="isCenterPage"/>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<style scoped>

* {
  outline: none;
  box-sizing: border-box;
}

html {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

img {
  max-width: 100%;
}

body {
  font-family: "Inter", sans-serif;
  background-color: #252954;
  color: #9b9ca7;
}

.wrapper {
  background-color: #0e0e23;
  display: flex;
  height: 100vh;
  overflow: hidden;
  margin: 0 auto;
}

.main-container {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  padding: 25px;
  overflow: auto;
}

.user-box {
  display: flex;
  height: 30%;
  margin-top: 16px;
}


.user-box .cards-header svg,
.user-box .cards-view svg {
  width: 24px;
}


.user-box .cards-header-date svg,
.user-box .cards-view-date svg {
  width: 30px;
}

.user-box .cards-header .cards-button svg,
.user-box .cards-view .cards-button svg {
  width: 16px;
  margin: 0 2px 0 0;
}


.cards-view > svg {
  margin-right: 12px;
}

@keyframes top {
  0% {
    transform: translateY(-30px);
    opacity: 0;
  }
  100% {
    opacity: 1;
    transform: none;
  }
}

@keyframes pieChart {
  100% {
    stroke-dashoffset: 0;
  }
}

.cards-header-date svg {
  color: #4154d1;
  background: #14183c;
  border-radius: 50%;
  padding: 5px;
  width: 30px;
}
.degree svg {
  width: 24px;
  margin-right: 12px;
}

.table .status svg {
  margin-right: 6px;
  width: 22px;
  height: 22px;
  padding: 3px;
  border-radius: 4px;
  background-color: #142940;
  color: currentColor;
}

.table .status.is-red svg {
  background: #2e2142;
  color: currentcolor;
}

.table .status.is-wait svg {
  background-color: transparent;
  color: currentcolor;
  animation: turn 2s linear infinite both;
}

@keyframes turn {
  100% {
    transform: rotate(1turn);
  }
}

.table th {
  font-size: 14px;
  font-weight: normal;
  padding-bottom: 16px;
}
.table th:nth-child(n+5) {
  padding: 0 10px 16px;
}
.table th:first-child {
  padding-left: 36px;
}
.table td {
  font-size: 15px;
  vertical-align: middle;
  padding: 8px 0;
}
.table td:last-of-type {
  width: 100px;
}
.table td:nth-child(n+5) {
  padding: 0 10px;
}
.table td:nth-last-of-type(2) svg {
  width: 16px;
}
.table input {
  appearance: none;
  width: 16px;
  height: 16px;
  border: 1px solid #9b9ca7;
  vertical-align: middle;
  background-color: transparent;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 16px;
  transition: 0.25s;
  background-size: 0;
  background-position: center;
}
.table input:checked {
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='24' height='24' stroke='%23fff' stroke-width='2' fill='none' stroke-linecap='round' stroke-linejoin='round' class='css-i6dzq1'%3E%3Cpolyline points='20 6 9 17 4 12'%3E%3C/polyline%3E%3C/svg%3E");
  background-color: #252954;
  background-size: 12px;
  background-repeat: no-repeat;
}

@keyframes fly {
  40% {
    transform: translate(-6px, -6px);
  }
  60% {
    transform: translate(-12px, -2px);
  }
  100% {
    transform: translate(0px, 0px);
  }
}

.credit-wrapper svg {
  width: 38px;
  padding: 3px;
  border-radius: 6px;
  margin-right: 12px;
}
.credit-wrapper path {
  width: 24px;
}


@media screen and (max-width: 1500px) {
  .wrapper {
    max-width: 1200px;
  }

  .activity {
    width: 49%;
    max-width: none;
  }

  .first-box {
    flex-wrap: wrap;
  }
}

@media screen and (max-width: 420px) {
  .destination-card + .destination-card {
    display: none;
  }
}
.user-box > * {
  animation: top 1s var(--delay) both;
}

</style>