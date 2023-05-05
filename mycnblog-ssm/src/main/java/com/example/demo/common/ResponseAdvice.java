package com.example.demo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;

/**
 * 设置统一返回格式
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HashMap<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("msg","");
        res.put("data",body);

        if(body instanceof String){//如果是 String 类型则将 result 转换为 String 类型
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if(body instanceof HashMap){//如果Body本身就是哈希表,直接返回即可
            return body;
        }

        return AjaxResult.success(body,"");//如果是其它类型则封装为 AjaxResult
    }
}
