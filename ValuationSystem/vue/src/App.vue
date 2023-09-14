<template>
  <div id="app">
    <Search/>
    <Type/>
    <BlogList/>
    <Footer/>
  </div>
</template>

<script>
import Search from "@/pages/HomeSearch/Search.vue";
import BlogList from "@/pages/HomeBlogList/BlogList.vue";
import Footer from "@/pages/HomeFooter/Footer.vue";
import Type from "@/pages/HomeType/Type.vue";

export default {
  name: 'App',
  components: {
    Search,
    Type,
    BlogList,
    Footer
  },
  data() {
    return {
      isReachBottom: false,
    }
  }
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
