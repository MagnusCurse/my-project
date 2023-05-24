//退出登录函数
function onExit(){
    if(confirm("是否退出登录?")){
        //发送 ajax 给后端
        jQuery.ajax({
           url:"/user/logout",
           type: "POST",
           data: {},//这里不需要传data
           success: function(result){
            //退出登录后跳转回登录页面
              if(result.code == 200 && result.data == 1){
                alert("退出登录成功");
                location.href = "login.html";
              }
           },

           error: function(err){
            //此时用户未登录,被拦截器拦截
              if(err != null && err.status == 401){//err不是你返回的那个哈希表了,里面没有data等值,只有status,所以用status判断
                alert("用户未登录,即将返回登录页面");
                location.href = "login.html";
              }
           }
         });
    } 
}