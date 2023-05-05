package com.example.demo.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回类型
 */
@Component
public class AjaxResult {
    public Object success(String msg,Object data){
        HashMap<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("msg",msg);
        res.put("data",data);
        return res;
    }

    public Object fail(String msg,Object data){
        HashMap<String,Object> res = new HashMap<>();
        res.put("code",-1);
        res.put("msg",msg);
        res.put("data",data);
        return res;
    }
}
