// 这个文件用来创建整个应用的路由器
import VueRouter from 'vue-router'

import Login from "@/pages/Login/Login.vue";
import Reg from "@/pages/Reg/Reg.vue";
import Home from "@/pages/Home/Home.vue";
import Create from "@/pages/Create/Create.vue";
import Detail from "@/pages/Detail/Detail.vue";
import Center from "@/pages/Center/Center.vue";
import Edit from "@/pages/CenterEdit/Edit.vue";
import Bind from "@/pages/CenterBind/Bind.vue";


// 创建一个路由器
export default new VueRouter({
    routes: [
        {
          path: "/home", // 路径
          component: Home,  // 组件名
            children: [
                {
                    path: "detail",
                    component: Detail,
                    // props 传递参数
                    props($route) {
                        return {id: $route.query.id }
                    }
                },
                {
                    path: "create",
                    component: Create,
                    // props 传递参数
                    props($route) {
                        return {
                                id: $route.query.id,
                                user_id: $route.query.user_id,
                                flag: $route.query.flag
                        }
                    }
                }
            ]
        },
        {
            path: '/center',
            component: Center,
            children: [
                {
                    path: "edit",
                    component: Edit
                },
                {
                    path: "bind",
                    component: Bind
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