<script>

export default {
  name: "Search",
  methods: {
    toPage(i) {
      if (i === 0) {
        location.href = "/blog-edit.html"
      } else if (i === 4) {
        location.href = "/info.html"
      } else if (i === 1){
        location.href = "/"
      }
    }
  }
}
</script>

<template>
  <div class="search-bar">
    <div class="city-btn">杭州 <i class="el-icon-arrow-down"></i></div>
    <div class="search-input">
      <el-input size="mini" placeholder="请输入商户名、地点">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
    </div>
    <div class="header-icon" @click="toPage(4)"><i class="el-icon-user"></i></div>
  </div>
</template>

<style scoped>
/*搜索框*/
.search-bar{
  background-color: #ff6633;
  color: white;
  display: flex;
  justify-content: space-around;
  height: 7%;
  align-items: center;
  padding: 0 8px;
}
.header-icon{
  /*width: 2%;*/
  font-size: 14px;
}
.search-input{
  width: 70%;
}
.type-list {
  height: 25%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
}
.type-box{
  width: 17%;
  text-align: center;
}
.type-box img {
  width: 100%;
}
.type-text{
  color: #111;
  font-size: 10px;
  margin-top: -8px;
  margin-bottom: 10px;
}
</style>