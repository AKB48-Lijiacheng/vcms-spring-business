package com.westcatr.vcms.component.webSocket;

import cn.hutool.core.util.IdUtil;
import com.westcatr.vcms.entity.SocketMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@ServerEndpoint(value = "/websocket/control")// control端的连接地址
public class WebSocketServerForControl {

   private static int onlineCount = 0;
   public static ConcurrentHashMap<String, WebSocketServerForControl> webSocketSet = new ConcurrentHashMap<>();

   // 与某个客户端的连接会话，需要通过它来给客户端发送数据
   private Session session;
   private  String id = "";
   private String userName;//登录信息的用户名

   /**
    * 连接建立成功调用的方法
    */
   @OnOpen
   public void onOpen(Session session) {
      this.session = session;
      Map<String, List<String>> parameterMap = session.getRequestParameterMap();
      String username = parameterMap.get("username").get(0);
      this.userName=username;
      id=username+":Control-"+ IdUtil.simpleUUID();
      System.out.println(id);
      webSocketSet.put(this.id, this);
      addOnlineCount();

      try {
         log.info("Control连接成功,当前连接人数："+ onlineCount);
         sendMessage(JSON.toJSONString(SocketMsg.message("连接成功")));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * 连接关闭调用的方法
    */
   @OnClose
   public void onClose() {
      webSocketSet.remove(this); // 从set中删除
      subOnlineCount(); // 在线数减1
   }

   /**
    * 收到客户端消息后调用的方法
    * @param message 客户端发送过来的消息
    */
   @OnMessage
   public void onMessage(String message, Session session) {
      try {
         sendtoUser(message);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    *
    * @param session
    * @param error
    */
   @OnError
   public void onError(Session session, Throwable error) {
      webSocketSet.remove(this);
      error.printStackTrace();
   }

   private void sendMessage(String message) throws IOException {
      this.session.getBasicRemote().sendText(message);
   }

   /**
    * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
    *
    * @param message
    * @throws IOException
    */
   public void sendtoUser(String message) throws IOException {
      log.info("客户端发来消息 id:+"+id+" "+message);
   }


   /**
    * 发送信息给所有人
    *
    * @param message
    * @throws IOException
    */
   public static void sendToAll(String message) throws IOException {
      for (String key : webSocketSet.keySet()) {
         try {
            WebSocketServerForControl web = webSocketSet.get(key);
            if (web.session.isOpen()) {
               webSocketSet.get(key).sendMessage(message);
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }



   public static synchronized int getOnlineCount() {
      return onlineCount;
   }

   public static synchronized void addOnlineCount() {
      WebSocketServerForControl.onlineCount++;
   }

   public static synchronized void subOnlineCount() {
      WebSocketServerForControl.onlineCount--;
   }

   public static ConcurrentHashMap<String, WebSocketServerForControl> getWebSocketSet() {
      return webSocketSet;
   }

   public static void setWebSocketSet(ConcurrentHashMap<String, WebSocketServerForControl> webSocketSet) {
      WebSocketServerForControl.webSocketSet = webSocketSet;
   }
}