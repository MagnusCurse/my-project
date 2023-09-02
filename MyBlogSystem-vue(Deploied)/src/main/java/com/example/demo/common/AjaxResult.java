package com.example.demo.common;

import java.util.HashMap;

/**
 * 设置统一返回对象
 */
public class AjaxResult {
    /**
     * 业务执行成功时
     */
    public static HashMap<String,Object> success(Object val,Object msg){
        HashMap<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("msg",msg);
        res.put("val",val);
        return res;
    }

    /**
     * 业务执行失败时
     */
    public static HashMap<String,Object> fail(Object code,Object msg){
        HashMap<String,Object> res = new HashMap<>();
        res.put("code",code);
        res.put("msg",msg);
        res.put("val","");
        return res;
    }
}
