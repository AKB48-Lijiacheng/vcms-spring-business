package com.westcatr.vcms.controller.query;

import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.QueryCondition;
import com.westcatr.rd.base.cwebmybatisbootstarter.dto.TimeDTO;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会议室预定表
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ConferenceRoomReservation查询对象", description="会议室预定表查询对象")
public class ConferenceRoomReservationQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "会议开始时间")
    @QueryCondition
    private Date startDateTime;

    @ApiModelProperty(value = "会议结束时间")
    @QueryCondition
    private Date endDateTime;

    @ApiModelProperty(value = "会议主题")
    @QueryCondition
    private String conferenceTheme;

    @ApiModelProperty(value = "所属会议室id")
    @QueryCondition
    private String roomId;

    @ApiModelProperty(value = "预定会议室名称")
    @QueryCondition
    private String roomName;

    @ApiModelProperty(value = "预定人")
    @QueryCondition
    private String reservedName;

    @ApiModelProperty(value = "传入所选日期")
    private Date selectDate;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
