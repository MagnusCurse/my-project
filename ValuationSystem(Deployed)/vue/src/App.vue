<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App'
}
</script>

<style>
html {
  font-size: 12px;
  width: 100%;
}
body{
  margin: 0;
}
html,body,#app{
  height: 100%;
}

#app {
  font-size: 12px;
  width: 100%;
  margin: 0;
  height: 100%;
}

/* 达人探店列表 */
.blog-img img{
  width: 100%;
  border-radius: 3px;
}
.blog-user-icon img{
  width: 100%;
}
.blog-liked img{
  width: 30%;
  height: 75%;
  margin-right: 2px;
}

/* 底部部分 */
.foot .add-btn {
  width: 38px;
  height: 38px;
  box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.1);
  border-radius: 18px;
}
</style>
