package com.westcatr.vcms.component.aop;

import com.alibaba.fastjson.JSONObject;
import com.westcatr.rd.base.acommon.domain.IUser;
import com.westcatr.rd.base.acommon.util.IUserUtil;
import com.westcatr.vcms.component.webSocket.WebSocketServerForAdmin;
import com.westcatr.vcms.component.webSocket.WebSocketServerForControl;
import com.westcatr.vcms.entity.SocketMsg;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author : lijiacheng
 * @description :配置Aop,当admin端更新了文件，通过websocket去通知control端去更新最新的文件内容
 * ---------------------------------
 * @since : Create in 2020/12/21 16:41
 */
@Aspect
@Component
@Slf4j
public class WebSocketMsgAop {

   @Autowired
   IUserUtil iUserUtil;
   /*
   execution表达式定义切入点
    */
   @Pointcut("execution(* *update*(..))")
   private void update() {}
   @Pointcut("execution(* *add*(..))")
   private void add() {}
   @Pointcut("execution(* *save*(..))")
   private void save() {}
   @Pointcut("execution(* *rem*(..))")
   private void del() {}

   @Pointcut("within(com.westcatr.vcms.service..*File* )")
   private void allFile() {}
   @Pointcut("within(com.westcatr.vcms.service..*Screen* )")
   private void allScreen() {}
//   @Pointcut("bean(FileTextServiceImpl)")
//   private void fileText() {}
//   @Pointcut("bean(FileServiceImpl)")
//   private void file() {}
//   @Pointcut("bean(FileGroupServiceImpl)")
//   private void fileGroup() {}
//   @Pointcut("bean(ScreenInfoServiceImpl)")
//   private void screenInfo() {}



   @Pointcut("del()||add()||update()||save()")
   private void methodMix(){}
   @Pointcut("(allScreen()||allFile())")
   private void classMix(){}




//   @Around("methodMix()&&classMix()")
   public Object doAround(ProceedingJoinPoint joinPoint) {
      Object result=null;//返回值
      try {
           log.info("切面方法执行了");
          result = joinPoint.proceed();//执行方法
         RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
         if (null==requestAttributes) {
            return null;
         }
         IUser user = iUserUtil.getIUser();
         Integer clientCode = user.getClientCode();//获取端的编号 0:admin 1:control

         if (clientCode==0){ //如果是admin修改了通知所有端去拿最新的数据
           WebSocketServerForControl.sendToAll(JSONObject.toJSONString(SocketMsg.fileGroupUpdate()));
            WebSocketServerForAdmin.sendToAll(JSONObject.toJSONString(SocketMsg.fileGroupUpdate()));
         }
         if (clientCode==1){//如果是Control修改了通知所有端去拿最新的数据,除了自己的control
           WebSocketServerForAdmin.sendToAll(JSONObject.toJSONString(SocketMsg.fileGroupUpdate()));
         }
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }
      return result;
   }

}

