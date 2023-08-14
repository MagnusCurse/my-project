<script>
import Header from "@/pages/HomeHeader/Header.vue";
import Content from "@/pages/HomeContent/Content.vue";
import LeftSide from "@/pages/HomeLeftSide/LeftSide.vue";
import User from "@/pages/HomeUser/User.vue";

export default {
  name: "Home",
  components: {
    Header,
    Content,
    LeftSide,
    User
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
          <!--   用户区域      -->
          <User/>
          <div class="activity card" style="--delay: .2s">
            <div class="title">User Activities</div>
            <div class="subtitle">Hoo - is an adaptive Online Courses Application with a wide range of course directions. The students will have a great possibility to study.</div>
            <div class="activity-links">
              <div class="activity-link active">Current User</div>
              <div class="activity-link notify">User Request</div>
            </div>
            <div class="destination">
              <div class="destination-card">
                <div class="destination-profile">
                  <img class="profile-img" src="https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80" alt="" />
                  <div class="destination-length">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
                      <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z" />
                      <circle cx="12" cy="10" r="3" />
                    </svg>
                    45.4m
                  </div>
                </div>
                <div class="destination-points">
                  <div class="point">Traffic Point</div>
                  <div class="sub-point">Brooklyn St, NY</div>
                </div>
              </div>
              <div class="destination-card">
                <div class="destination-profile">
                  <img class="profile-img" src="https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1400&q=80" alt="" />
                  <div class="destination-length">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
                      <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z" />
                      <circle cx="12" cy="10" r="3" />
                    </svg>
                    42.8m
                  </div>
                </div>
                <div class="destination-points">
                  <div class="point">Pickup Point</div>
                  <div class="sub-point">Maryland 17, NY</div>
                </div>
              </div>
            </div>
          </div>
        </div>
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
  padding-left: 0;
}

.user-box {
  margin-top: 25px;
  display: flex;
}
.user-box + .user-box {
  margin-top: 20px;
}

.user-box .cards-header svg,
.user-box .cards-view svg {
  width: 24px;
}

.user-box .cards-header .title,
.user-box .cards-view .title {
  margin: 0 16px;
  font-size: 15px;
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

.user-box .cards-header .date-wrapper .title,
.user-box .cards-view .date-wrapper .title {
  margin: 0 16px;
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

.card {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  padding: 40px 30px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  max-height: 430px;
  width: 100%;
}
.card .title {
  font-size: 16px;
  font-weight: 500;
}
.card .subtitle {
  font-size: 13px;
  line-height: 1.6em;
}
.card + .card {
  margin-left: 20px;
}

.activity {
  max-width: 480px;
}
.activity .title {
  margin-bottom: 20px;
}
.activity-links {
  display: flex;
  align-items: center;
  margin-top: auto;
  font-size: 15px;
}
.activity-link {
  padding-bottom: 10px;
  position: relative;
  cursor: pointer;
  transition: 0.3s;
}
.activity-link + .activity-link {
  margin-left: 25px;
}
.activity-link + .activity-link:before {
  content: "";
  position: absolute;
  width: 5px;
  height: 5px;
  background-color: #ef415c;
  top: -2px;
  right: -8px;
  border-radius: 50%;
}
.activity-link + .activity-link:hover:after {
  content: "";
  position: absolute;
  width: 22px;
  height: 2px;
  background: #4255d4;
  left: 0;
  bottom: 0;
}
.activity-link + .activity-link:hover {
  color: #bebec4;
  -webkit-text-stroke: 0.3px;
}
.activity-link.active {
  color: #bebec4;
  font-weight: 500;
}
.activity-link.active:before {
  content: "";
  position: absolute;
  width: 22px;
  height: 2px;
  background: #4255d4;
  left: 0;
  bottom: 0;
}

.destination {
  display: flex;
  align-items: center;
  margin-top: auto;
}
.destination-card {
  background: #1a1d3a;
  background: linear-gradient(45deg, #1a1d3a 0%, #212752 100%);
  padding: 20px;
  width: 100%;
  border-radius: 6px;
}
.destination-card + .destination-card {
  margin-left: 20px;
  background: #1a1d3a;
  background: linear-gradient(325deg, #1a1d3a 0%, #212752 100%);
}
.destination-profile {
  display: flex;
  align-items: center;
  font-size: 14px;
}
.destination-profile svg {
  width: 18px;
  flex-shrink: 0;
  margin-right: 8px;
}
.destination-length {
  margin-left: auto;
  display: flex;
  align-items: center;
  font-size: 13px;
}
.destination-points {
  margin-top: 30px;
}

.profile-img {
  width: 46px;
  height: 46px;
  object-fit: cover;
  border-radius: 50%;
  padding: 2px;
  border: 1px solid #bebec4;
  flex-shrink: 0;
}

.point {
  font-size: 14px;
  color: #bebec4;
  font-weight: 500;
}

.sub-point {
  font-size: 13px;
  margin-top: 4px;
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