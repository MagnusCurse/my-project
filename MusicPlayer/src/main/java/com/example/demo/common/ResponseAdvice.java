package com.example.demo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回格式设置
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice{
    @Autowired
    AjaxResult ajaxResult;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true; // 这里要记得改为 true 才会生效哦
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        /**
         * 这里 ResponseEntity是SpringBoot本来就封装好的类型,如果再进行封装就会报错
         */
        if (returnType.getParameterType().equals(ResponseEntity.class)) { // 如果 returnType 是 ResponseEntity 类型，则直接返回 body,要不然这里会报错
            return body;
        }

        HashMap<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("msg","");
        res.put("data",body);

        if(body instanceof String){ // 如果本身就是字符串类型,将 res 转换为字符串返回
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if(body instanceof HashMap){ // body本身就是哈希表,直接返回即可
            return body;
        }
        return ajaxResult.success("",body); // 如果是其它类型,将其封装为哈希表
    }
}
