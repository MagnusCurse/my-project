package com.example.demo.websocket;

import com.example.demo.utils.MessageUtils;
import com.example.demo.websocket.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndPoint {
    // 用来存储每一个客户端对象对应的 ChatEndPont 对象
    private static final Map<String,ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();

    // 声明 session 对象, 通过该对象可以发送消息给指定用户
    private Session session;

    // 声明 HttpSession 对象, 之前在 HttpSession 对象中存储了用户名
    private HttpSession httpSession;

    /* 用于向全体用户发送广播 */
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

    /* 连接建立的时候被调用 */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // TODO 将局部 session 对象赋值给成员 session
        this.session = session;
        // TODO 获取 HttpSession 对象 : 需要通过 GetHttpSessionConfigurator 存储后才能从 config 中获取对象
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

    /* 接收到客户端发送的数据的时候被调用 */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            // TODO 将 String 转化为 Message 对象
            ObjectMapper mapper = new ObjectMapper();
            // 获取 Message 对象
            Message msg = mapper.readValue(message,Message.class);
            // 获取数据要发送给的用户
            String toName = msg.getToName();
            // 获取消息数据
            String msgData = msg.getMessage();
            // TODO 获取当前登录的用户
            // ERR 同一个浏览器目前会有用户名重复的问题: 因为每个客户端 ( 浏览器 ) 只有一个 Session, 可以改进一下获取用户的方式, 如不要单独使用 " user " 作 key
            String curUserName = (String) httpSession.getAttribute("user");
            System.out.println("当前用户是:" + curUserName);
            // 获取推送给指定用户的消息格式的数据
            String resultMessage = MessageUtils.getMessage(false,curUserName,msgData);
            // TODO 发送数据给 toName 用户
            onlineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
