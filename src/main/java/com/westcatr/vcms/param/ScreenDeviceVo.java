package com.westcatr.vcms.param;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScreenDeviceVo {

    @ApiModelProperty(value = "设备ip")
    private String deviceIp;

    @ApiModelProperty(value = "备注")
    private String remake;

    @ApiModelProperty(value = "最后在线时间")
    private Date updateTime;

    @ApiModelProperty(value = "离线时长")
    private Long offlineDuration;

    @ApiModelProperty(value = "当前是否在线")
    private boolean tfOnline=false;
}