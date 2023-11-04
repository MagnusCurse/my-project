<script>

export default {
  name: "Footer",
  methods: {
    toPage(i) {
      if (i === 0) {
        this.$router.push("/edit")
      } else if (i === 4) {
        this.$router.push("/center")
      } else if (i === 1){
        this.$router.push("/home")
      }
    }
  },
  props: ['activeBtn'],
}
</script>

<template>
  <div class="foot">
    <div class="foot-box" :class="{active: activeBtn === 1}" @click="toPage(1)">
      <div class="foot-view"><i class="el-icon-s-home"></i></div>
      <div class="foot-text">  </div>
    </div>
<!--    <div class="foot-box" :class="{active: activeBtn === 2}" @click="toPage(2)">-->
<!--      <div class="foot-view"><i class="el-icon-map-location"></i></div>-->
<!--      <div class="foot-text">地图</div>-->
<!--    </div>-->

    <div class="foot-box" @click="toPage(0)">
      <div style="background: #545d68; border-radius: 50%">
         <span style="color: #cdd9e5; font-size: 12px">
           Publish
         </span>
      </div>
    </div>

<!--    <div class="foot-box" :class="{active: activeBtn === 3}" @click="toPage(3)">-->
<!--      <div class="foot-view"><i class="el-icon-chat-dot-round"></i></div>-->
<!--      <div class="foot-text">消息</div>-->
<!--    </div>-->
    <div class="foot-box" :class="{active: activeBtn === 4}" @click="toPage(4)">
      <div class="foot-view"><i class="el-icon-user"></i></div>
      <div class="foot-text">  </div>
    </div>
  </div>
</template>

<style scoped>
/* 底部部分 */
.foot {
  width: 100%;
  height: 8%;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 -1px 2px 1px rgba(0, 0, 0, 0.1);
  bottom: 0;
  background: #373e47;
}

.foot .add-btn {
  width: 38px;
  height: 38px;
  box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.1);
  border-radius: 18px;
}
.foot-box{
  width: 20%;
  color: #82848a;
  text-align: center;
}
.foot-view {
  font-size: 26px;
}
.foot-text{
  font-size: 8px;
  margin-top: -4px;
  margin-bottom: 4px;
}
.foot-text, .foot-view{
  text-align: center;
}
.active{
  color: #ff6633;
}

</style>