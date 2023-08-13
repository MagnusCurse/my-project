<template>
    <div id="app">
      <router-view></router-view> <!-- 组件呈现的位置  -->
    </div>
</template>

<script>
import Login  from "@/pages/login.vue";
import Reg from "@/pages/reg.vue";
import Home from "@/pages/home.vue";

export default {
   name: 'app',
   components: {
      Login,
      Reg,
      Home
   }
}

</script>

<style>
  html {
    height: 100%;
  }
  body {
    margin:0;
    padding:0;
    font-family: sans-serif;
    background: linear-gradient(#141e30, #243b55);
  }
</style>

