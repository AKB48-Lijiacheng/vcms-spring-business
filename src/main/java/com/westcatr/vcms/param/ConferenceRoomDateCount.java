package com.westcatr.vcms.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConferenceRoomDateCount {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("预定日期")
    private Date schedulTime;

    @ApiModelProperty("预定数量")
    private Integer schedulCount;
}