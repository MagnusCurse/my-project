import VueRouter from "vue-router";
import Home from "@/pages/Home/Home.vue";
import Login from "@/pages/Login/Login.vue";
import Center from "@/pages/Center/Center.vue";


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
        }
    ]
})