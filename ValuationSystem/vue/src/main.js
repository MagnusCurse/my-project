import Vue from 'vue'
import App from './App.vue'
import axios from "axios";

// 引入 ElementUI
import ElementUI from 'element-ui';
// 引入 ElementUI 样式
import 'element-ui/lib/theme-chalk/index.css';

// 安装 ElementUI 插件
Vue.use(ElementUI);

// 配置 axios 的默认路径
axios.defaults.baseURL = "http://localhost:8081"
// 前端解决跨域导致的 Session 丢失问题
axios.defaults.withCredentials = true;

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
