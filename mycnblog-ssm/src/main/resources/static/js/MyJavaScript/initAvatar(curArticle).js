
// 初始化当前用户头像
var app = new Vue({
    el: "#photo",
    data: {
    	imageUrl: "img/avatar/default-image.png", // 显示的图片 
    },
    methods: {
        initAvatar : function() { // 初始化当前用户头像
            var self = this; // 保存当前对象
            var id = getURLParam("id");
            axios.post(
            "/user/mycontentinfo2",{ id : id }) // 这里给后端传输的是一个 body，后端代码需要用 body 的形式去接收
            .then(function(response){
                if(response.data.data.avatar_url != null){
                    self.imageUrl = response.data.data.avatar_url;
                }
            }).catch(function(error){
                
            });
        }
    },
    mounted: function() {
          this.initAvatar(); // 加载页面的时候初始化头像
    },
 });
             