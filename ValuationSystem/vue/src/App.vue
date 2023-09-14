<template>
  <div id="app">
    <Search/>
    <div class="type-list">
      <div class="type-box" v-for="t in types" :key="t.id" @click="toShopList(t.id, t.name)">
        <div class="type-view"><img :src="'/imgs/' + t.icon" alt=""></div>
        <div class="type-text">{{t.name}}</div>
      </div>
    </div>
    <BlogList/>
    <Footer/>
  </div>
</template>

<script>
import axios from "axios";

import Search from "@/pages/HomeSearch/Search.vue";
import BlogList from "@/pages/HomeBlogList/BlogList.vue";
import Footer from "@/pages/HomeFooter/Footer.vue";

export default {
  name: 'App',
  components: {
    Search,
    BlogList,
    Footer
  },
  data() {
    return {
      isReachBottom: false,
      types: [], // 类型列
      current: 1,// blog的页码
    }
  },
  methods: {
    queryTypes() {
      axios.get("/shop-type/list")
          .then(({data}) => {
            this.types = data;
          })
          .catch(err => {
            this.$message.error(err)
          })
    },
    toShopList(id, name) {
      location.href = "/shop-list.html?type=" + id + "&name=" + name
    },
  },
  mounted() {
    // 查询类型
    this.queryTypes();

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

.foot .add-btn {
  width: 38px;
  height: 38px;
  box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.1);
  border-radius: 18px;
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
