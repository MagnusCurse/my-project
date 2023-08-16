<template>
    <div class="app">
      <router-view></router-view> <!-- 组件呈现的位置  -->
    </div>
</template>

<script>

export default {
   name: 'app',
   components: {

   }
}

</script>

<style>
  .app {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    padding: 0;
    margin: 0;
    font-family: sans-serif;
    background: linear-gradient(#141e30, #243b55);
  }
</style>

