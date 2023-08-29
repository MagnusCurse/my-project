import axios from "axios";

export const mixin = {
    methods: {
        // 初始化用户头像
        initAvatar() {
            const originThis = this; // 缓存 this
            // 发送请求给后端
            axios({
                url: "http://localhost:9090/user/init-avatar",
                method: "get"
            }).then(function (response) {
                if(response.data.code == 200 && response.data.val != null) {
                    originThis.imageUrl = require("@/img/avatar/" + response.data.val);
                } else {
                    alert("初始化头像失败,请重试");
                }
            }).catch(function (error) {
                console.log(error);
                alert("出现异常,详情见控制台");
            })
        },
    }
}