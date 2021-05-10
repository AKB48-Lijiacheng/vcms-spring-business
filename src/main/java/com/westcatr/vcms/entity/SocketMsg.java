package com.westcatr.vcms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : lijiacheng
 * @description :WebSocketMessage
 * ---------------------------------
 * @since : Create in 2020/12/18 11:07
 */
@Data
@ApiModel("webSocket返回体")
public class SocketMsg<T> implements Serializable {

   /**
    * 消息
    */
   @ApiModelProperty(value = "消息")
   private T message;
   /**
    * 类型
    */
   @ApiModelProperty(value = "类型")
   private String type;
   /**
    * 状态
    */
   @ApiModelProperty(value = "状态")
   private String status;
   /**
    * id
    */
   @ApiModelProperty(value = "修改文件的id")
   private String id;




   public static <T> SocketMsg<T> update(String type, String id) {
      SocketMsg<T> web = new SocketMsg<>();
      web.setMessage(null);
      web.setId(id);
      web.setType(type);
      web.setStatus("update");
      return web;
   }

   public static <T> SocketMsg<T> add(String type, String id) {
      SocketMsg<T> web = new SocketMsg<>();
      web.setMessage(null);
      web.setId(id);
      web.setType(type);
      web.setStatus("add");
      return web;
   }

   public static <T> SocketMsg<T> remove(String type, String id) {
      SocketMsg<T> web = new SocketMsg<>();
      web.setMessage(null);
      web.setId(id);
      web.setType(type);
      web.setStatus("remove");
      return web;
   }


   public static <T> SocketMsg<T> updateMessage(String type, String id) {
      SocketMsg<T> web = new SocketMsg<>();
      web.setMessage(null);
      web.setId(id);
      web.setType(type);
      web.setStatus("update");
      return web;
   }
   public static <T> SocketMsg<T> fileGroupUpdate() {
      SocketMsg<T> web = new SocketMsg<>();
      web.setMessage(null);
      web.setId(null);
      web.setType("fileGroup");
      web.setStatus("update");
      return web;
   }

   public static <T> SocketMsg<T> message(T message) {
      SocketMsg<T> web = new SocketMsg<>();
      web.setMessage(message);
      web.setId(null);
      web.setType(null);
      web.setStatus(null);
      return web;
   }



}
