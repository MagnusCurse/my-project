//获取当前 url 的某个参数
function getURLParam(key){
    var params = location.search;
        if(params.indexOf("?") >= 0){
            params = params.substring(1);
            var paramArr = params.split('&');
            for(var i = 0; i < paramArr.length; i++){
                var namevalues = paramArr[i].split("=");
                if(namevalues[0] == key){
                        return namevalues[1];
                }
            }
    }else{
        return "";
    }
}

//用于截取字符串
function mySubString(content){
    if(content.length > 80){
        return content.substr(0,80);
    }
    return content;
}



