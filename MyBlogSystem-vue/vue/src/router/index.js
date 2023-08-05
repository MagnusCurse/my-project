// 这个文件用来创建整个应用的路由器
import VueRouter from 'vue-router'

import Login from "@/pages/login.vue";
import Reg from "@/pages/reg.vue";
import Home from "@/pages/home.vue";

// 创建一个路由器
export default new VueRouter({
    routes: [
        {
          path: "/home", // 路径
          component: Home  // 组件名
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/reg',
            component: Reg
        }
    ]
})