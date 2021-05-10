package com.westcatr.vcms.component.webSocket;

import com.westcatr.vcms.service.MobileService;
import com.westcatr.vcms.service.ScreenInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.westcatr.rd.base.acommon.vo.IResult;
import com.westcatr.vcms.entity.ScreenInfo;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
// 访问服务端的url地址
@Slf4j
@ServerEndpoint(value = "/websocket/{id}")
public class WebSocketServer {

    private static ScreenInfoService screenInfoService;

    private static MobileService mobileService;

   private static int onlineCount = 0;
   public static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

   // 与某个客户端的连接会话，需要通过它来给客户端发送数据
   private Session session;
   private String id = "";


   @Autowired
    public void setMobileService(MobileService mobileService) {
        WebSocketServer.mobileService = mobileService;
    }

    @Autowired
    public void setScreenInfoService(ScreenInfoService screenInfoServicee) {
        WebSocketServer.screenInfoService = screenInfoServicee;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        this.session = session;
        this.id = id;
        webSocketSet.put(id, this);
        addOnlineCount();
        //注册设备
         id= id.split("-")[0];
        String s = mobileService.registerDevice(id);
        try {
           log.info("连接成功,当前连接人数："+onlineCount);
            sendMessage(JSON.toJSONString(IResult.ok("连接成功")));
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
     *
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
        error.printStackTrace();
    }

    public  void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
       log.info(message);
    }

    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     * 
     * @param message
     * @throws IOException
     */
    public void sendtoUser(String message) throws IOException {
        if (!JSON.isValidObject(message)) {
            webSocketSet.get(id).sendMessage("传入json格式错误!!!");
            return;
        }
        JSONObject jObject = JSON.parseObject(message);
        String device = jObject.getString("device");
        ScreenInfo param = JSON.toJavaObject(jObject, ScreenInfo.class);
        String deviceId = jObject.getString("deviceId");
        // 如果是显示端，则用每5秒一次的接口
        if (device.equals("view")) {
            ScreenInfo screenInfo = screenInfoService.isaveOrUpdate(param);
            if (webSocketSet.get(deviceId + "-view") != null) {
                webSocketSet.get(deviceId + "-view").sendMessage(JSON.toJSONString(IResult.ok(screenInfo)));
            }
        } else {// 如果为admin或者control端,则用修改接口
            Boolean result = screenInfoService.iupdate(param);
            ScreenInfo screenInfo = screenInfoService.isaveOrUpdate(param);
            if (webSocketSet.get(deviceId + "-admin") != null || webSocketSet.get(deviceId + "-control") != null) {
                if (result) {
                    webSocketSet.get(deviceId + "-view").sendMessage(JSON.toJSONString(IResult.ok(screenInfo)));
                    webSocketSet.get(deviceId + "-admin").sendMessage(JSON.toJSONString(IResult.auto(result)));

                } else {
                    webSocketSet.get(deviceId).sendMessage("设备信息更新失败");
                }
            }
        }
    }

    /**
     * 发送信息给所有人
     * 
     * @param message
     * @throws IOException
     */
    public void sendtoAll(String message) throws IOException {
        for (String key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static ConcurrentHashMap<String, WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(ConcurrentHashMap<String, WebSocketServer> webSocketSet) {
        WebSocketServer.webSocketSet = webSocketSet;
    }
}