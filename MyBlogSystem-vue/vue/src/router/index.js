// 这个文件用来创建整个应用的路由器
import VueRouter from 'vue-router'

import Login from "@/pages/Login/Login.vue";
import Reg from "@/pages/Reg/Reg.vue";
import Home from "@/pages/Home/Home.vue";
import Create from "@/pages/Create/Create.vue";
import Content from "@/pages/HomeContent/Content.vue";

// 创建一个路由器
export default new VueRouter({
    routes: [
        {
          path: "/home", // 路径
          component: Home,  // 组件名
            children: [
                {
                  path: "content",
                  component: Content
                },
                {
                    path: "create",
                    component: Create
                }
            ]
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