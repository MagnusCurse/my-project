<template>
    <div>
    <div class="row">
      <Header/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- 原始 HTML 实现页面跳转 -->
          <!-- <a class="list-group-item active" href="./about.html">About</a>
          <a class="list-group-item" href="./home.html">Home</a> -->

          <!--  -->
          <router-link class="list-group-item" to="/about" active-class="active">About</router-link>
          <router-link class="list-group-item" to="/home" active-class="active">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 组件呈现的位置  -->
            <router-view></router-view>
              
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Home from './pages/home.vue'
import About from './pages/about.vue'
import Header from './components/header.vue'

  export default {
     name: 'app',
     components: {
        Home,
        About,
        Header
     }
  }

</script>

<style scoped>
 
</style>

