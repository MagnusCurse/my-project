import Vue from 'vue'
import App from './App.vue'
import axios from "axios";
// 引入 VueRouter
import VueRouter from 'vue-router'
// 使用 VueRouter
Vue.use(VueRouter)

// 引入 ElementUI
import ElementUI from 'element-ui';
// 引入 ElementUI 样式
import 'element-ui/lib/theme-chalk/index.css';
import router from "@/router";

// 安装 ElementUI 插件
Vue.use(ElementUI);

// 配置 axios 的默认路径
axios.defaults.baseURL = "/api"

// 前端解决跨域导致的 Session 丢失问题
axios.defaults.withCredentials = true;

// 添加请求拦截器
axios.interceptors.request.use(
    (config) => {
      const token = sessionStorage.getItem('token'); // 从 sessionStorage 中获取 token
      if (token) {
        config.headers['authorization'] = token; // 给请求头新增一个字段 authorization, 添加 token 到请求头中
      } else {
          //
          alert("当前用户未登录!");
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);



Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
