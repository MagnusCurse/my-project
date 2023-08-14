<script>
import Recommend from "@/pages/Recommend/Recommend.vue";
export default {
  name: "Content",
  components: {
    Recommend
  }
}
</script>

<template>
  <div class="activity card" style="--delay: .2s">
    <div class="title">User Activities</div>
    <div class="subtitle">Hoo - is an adaptive Online Courses Application with a wide range of course directions. The students will have a great possibility to study.</div>
    <div class="activity-links">
      <div class="activity-link active">Current User</div>
      <div class="activity-link notify">User Request</div>
    </div>
    <!--  推荐用户区域  -->
    <Recommend/>

  </div>
</template>

<style scoped>

.activity {
  margin: 5px;
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

.card {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  padding: 40px 30px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  height: 100%;
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


</style>