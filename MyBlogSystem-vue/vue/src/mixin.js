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
                if(response.data.code == 200 && response.data.val != "") {
                    originThis.imageUrl = require("@/img/avatar/" + response.data.val);
                } else if(response.data.val == ""){
                    originThis.imageUrl = require("@/img/avatar/Default.png");
                } else {
                    alert("初始化头像失败,请重试");
                }
            }).catch(function (error) {
                console.log(error);
                alert("出现异常,详情见控制台");
            })
        },
        // 得到当前名为 "xxx" 的 query 参数
        getURLParam(key) {
            let hash = location.hash;
            if (hash.indexOf("?") >= 0) {
                hash = hash.substring(hash.indexOf("?") + 1);
                let paramArr = hash.split('&');
                for (let i = 0; i < paramArr.length; i++) {
                    let nameValuePair = paramArr[i].split("=");
                    if (nameValuePair.length === 2 && decodeURIComponent(nameValuePair[0]) === key) {
                        return decodeURIComponent(nameValuePair[1]);
                    }
                }
            }
            return "";
        }
    },

}