<script>

export default {
  name: "Header",
  methods: {
    goBack() {
      history.back();
    }
  }
}
</script>

<template>
  <div class="header">
    <div class="header-back-btn" @click="goBack" ><i class="el-icon-caret-left"></i></div>
    <div class="header-title" style="font-weight: bold;"></div>
  </div>
</template>

<style scoped>
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
</style>