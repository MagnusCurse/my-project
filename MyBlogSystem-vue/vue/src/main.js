// 引入 vue,这个 Vue 是精简版本的,没有模板解析器 template
import Vue from 'vue'
// 引入 App
import App from './App.vue'
// 引入 VueRouter
import VueRouter from 'vue-router'
// 使用 VueRouter
Vue.use(VueRouter)

// 引入路由器
import router from './router/index'
// 引入 font-awesome
import '@fortawesome/fontawesome-free/css/all.css';
// 引入 ElementUI
import ElementUI from 'element-ui';
// 引入 ElementUI 样式
import 'element-ui/lib/theme-chalk/index.css';
// 引入 ElementTipTap 富文本编译器
import { ElementTiptapPlugin } from 'element-tiptap';
// 引入 ElementTipTap 样式
import 'element-tiptap/lib/index.css';
// 引入 Buefy 组件库
import Buefy from 'buefy'
// 引入 Buefy css
import 'buefy/dist/buefy.css'
// 引入 Axios
import axios from "axios";

// 安装 ElementUI 插件
Vue.use(ElementUI);
// 使用 Buefy 组件库
Vue.use(Buefy)
// 安装 ElementTipTap 插件
Vue.use(ElementTiptapPlugin, {
    /* 插件配置项 */
    lang: "en", // see i18n
    spellcheck: true, // can be overwritten by editor prop
});
// 配置 axios 的默认路径
Vue.use(axios)
axios.defaults.baseURL = "http://localhost:9090"

// 前端解决跨域导致的 Session 丢失问题
axios.defaults.withCredentials = true;

Vue.config.productionTip = false;

new Vue({
    el: '#app',
    router: router,
    // 将组件 App 放入容器中
    render: h => h(App),
    beforeCreate(){ // 在模板解析之前,创建全局事件总线
      Vue.prototype.$bus = this; // beforeCreate 的 this 就是 vm
    }
})
