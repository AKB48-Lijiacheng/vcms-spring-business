package com.westcatr.vcms.param;

import lombok.Data;

@Data
public class SocketBase {

    private String timestamp;

    private String type;

    private String deviceId;

    private String socketId;
}