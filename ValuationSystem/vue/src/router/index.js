import VueRouter from "vue-router";
import Home from "@/pages/Home/Home.vue";
import Login from "@/pages/Login/Login.vue";
import Center from "@/pages/Center/Center.vue";
import Shop from "@/pages/Shop/Shop.vue";


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
        }
    ]
})