// 这个文件用来创建整个应用的路由器
import VueRouter from 'vue-router'

import Home from '../pages/home.vue'
import About from '../pages/about.vue'

// 创建一个路由器
export default new VueRouter({
    routes: [
        {
            path: '/about', // 路径
            component: About // 组件名
        },
        {
            path: '/home',
            component: Home
        }
    ]
})