package com.example.demo.websocket;

import com.example.demo.utils.MessageUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
@Component
public class ChatEndPoint {
    // 用来存储每一个客户端对象对应的 ChatEndPont 对象
    private static final Map<String,ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();

    // 声明 session 对象, 通过该对象可以发送消息给指定用户
    private Session session;

    // 声明 HttpSession 对象, 之前在 HttpSession 对象中存储了用户名
    private HttpSession httpSession;

    // 连接建立的时候被调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // TODO 将局部 session 对象赋值给成员 session
        this.session = session;
        // TODO 获取 HttpSession 对象
        this.httpSession  = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        // TODO 从 HttpSession 中获取用户名
        String username = (String) httpSession.getAttribute("user");
        // TODO 将当前对象存储到容器中
        onlineUsers.put(username,this);
        // TODO 将当前在线用户的用户名推送给所有客户端
        // 获取消息
        String message = MessageUtils.getMessage(true,null,onlineUsers.keySet());
        // 调用方法进行系统消息的推送
        broadcastAllUser(message);
    }

    private void broadcastAllUser(String message){
        Set<String> names = onlineUsers.keySet();
        for(String name : names) {
            // TODO  根据 name 获取 ChatEndPoint 对象
            ChatEndPoint chatEndPoint = onlineUsers.get(name);
            try {
                // TODO 将消息发送给该在线用户
                chatEndPoint.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
