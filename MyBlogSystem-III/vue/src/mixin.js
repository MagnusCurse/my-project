import axios from "axios";

export const mixin = {
    methods: {
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
    }
}

export const commonMixin = {
    methods: {
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
    }
}

export const blogMixin = {
    methods: {
        // 查询博客函数 / 过滤器
        search() {
            if(this.title == "") {
                this.initBlogs();
            }
            this.blogs = this.blogs.filter((blog) => {
                const blogTitle = blog.title.toLowerCase();
                const searchKey = this.title.toLowerCase();
                // 使用includes()方法检查标题是否包含关键字
                return blogTitle.includes(searchKey);
            })
        }
    }
}

export const userMixin = {
    methods: {
        // 初始化用户头像
        initAvatar() {
            const originThis = this; // 缓存 this
            // 发送请求给后端
            axios({
                url: "user/init-avatar",
                method: "get"
            }).then(function (response) {
                if(response.data.code == 200 && response.data.val != "") {
                    originThis.imageUrl = "/img/avatar/" + response.data.val;
                } else if(response.data.val == ""){
                    originThis.imageUrl = "/img/avatar/Default.png";
                } else {
                    alert("初始化头像失败,请重试");
                }
            }).catch(function (error) {
                console.log(error);
                alert("出现异常,详情见控制台");
            })
        },
        // 初始化用户信息
        initUserInfo() {
            const originThis = this; // 缓存 this
            // 发送请求给后端
            axios({
                url: "user/inti-user-info",
                method: "get"
            }).then(function (response) {
                if(response.data.code == 200 && response.data.val != "") {
                    const user = response.data.val;
                    if(user.username != null) {
                        originThis.username = user.username;
                    }
                } else {
                    alert("初始化用户信息失败,请重试");
                }
            }).catch(function (error) {
                console.log(error);
                alert("出现异常,详情见控制台");
            })
        }
    }
}
