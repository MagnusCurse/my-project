<script>
import Type from "@/pages/HomeType/Type.vue";
import Footer from "@/pages/HomeFooter/Footer.vue";
import BlogList from "@/pages/HomeBlogList/BlogList.vue";
import Search from "@/pages/HomeSearch/Search.vue";

export default {
  name: "Home",
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

<template>
  <div class="home">
    <Search/>
    <Type/>
    <BlogList/>
    <Footer/>
  </div>
</template>

<style scoped>
.home {
  height: 100%;
}
</style>