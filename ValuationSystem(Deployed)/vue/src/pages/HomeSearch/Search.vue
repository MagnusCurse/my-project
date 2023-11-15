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
<!-- note 地区选择功能:  <div class="city-btn">杭州<i class="el-icon-arrow-down"></i></div>  -->
    <div class="search-input">
      <el-input size="medium" placeholder="请输入商户名、地点" style="width: 100%">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
    </div>
  </div>
</template>

<style scoped>
/*搜索框*/
.search-bar{
  background-color: #373e47;
  display: flex;
  justify-content: space-around;
  height: 7%;
  align-items: center;
  padding: 0 8px;
}

.search-input{
  width: 70%;
}

.type-box img {
  width: 100%;
}

</style>