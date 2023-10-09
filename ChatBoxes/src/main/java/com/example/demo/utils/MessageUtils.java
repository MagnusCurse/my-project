package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.example.demo.websocket.entity.ResultMessage;


/**
 * @ClassName: MessageUtils
 * @Description: 封装 json 格式消息的工具类
 */
public class MessageUtils {

    public static String getMessage(boolean isSystemMessage,String fromName, Object message) {

        ResultMessage result = new ResultMessage();
        result.setSystem(isSystemMessage);
        result.setMessage(message);
        if(fromName != null) {
            result.setFromName(fromName);
        }
        return JSON.toJSONString(result);
    }
}
