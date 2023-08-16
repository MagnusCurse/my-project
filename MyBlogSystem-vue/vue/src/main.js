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
// 引入 ElementTipTap 富文本编译器
import { ElementTiptapPlugin } from 'element-tiptap';
// 引入 ElementTipTap 样式
import 'element-tiptap/lib/index.css';
// 引入 Buefy 组件库
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'

// 使用 Buefy 组件库
Vue.use(Buefy)
// 安装 ElementTipTap 插件
Vue.use(ElementTiptapPlugin, {
    /* 插件配置项 */
    lang: "en", // see i18n
    spellcheck: true, // can be overwritten by editor prop
});

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
