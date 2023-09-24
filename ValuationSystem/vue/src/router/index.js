import VueRouter from "vue-router";
import Home from "@/pages/Home/Home.vue";
import Login from "@/pages/Login/Login.vue";
import Center from "@/pages/Center/Center.vue";
import OtherCenter from "@/pages/OtherCenter/Center.vue"
import Shop from "@/pages/Shop/Shop.vue";
import ShopDetail from "@/pages/ShopDetail/Detail.vue";
import BlogDetail from "@/pages/BlogDetail/Detail.vue"
import Edit from "@/pages/Edit/Edit.vue";


export default new VueRouter({
    routes: [
        {
            path: "/home",
            component: Home
        },
        {
            path: "/center",
            component: Center
        },
        {
            path: "/other-center",
            component: OtherCenter,
            props($route) {
                return {
                    id: $route.query.id
                }
            }
        },
        {
            path: "/login",
            component: Login
        },
        {
            path: "/shop",
            component: Shop,
            props($route) {
                return {
                    type: $route.query.type,name: $route.query.name
                }
            }
        },
        {
          path: "/edit",
          component: Edit
        },
        {
            path: "/detail",
            component: ShopDetail,
            props($route) {
                return {
                    id: $route.query.id
                }
            }
        },
        {
            path: "/blog-detail",
            component: BlogDetail,
            props($route) {
                return {
                    id: $route.query.id
                }
            }
        }
    ]
})