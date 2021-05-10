package com.westcatr.vcms.param;

import com.westcatr.vcms.entity.ScreenInfo;

import lombok.Data;

public class SocketScreenInfo extends ScreenInfo {
    private String timestamp;

    private String type;

    private String socketId;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    
}