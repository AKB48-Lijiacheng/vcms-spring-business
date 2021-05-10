package com.westcatr.vcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@TableName("vc_conference_room_reservation")
@ApiModel(value="ConferenceRoomReservation对象", description="会议室预定表")
public class ConferenceRoomReservation extends Model<ConferenceRoomReservation> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "会议开始时间")
    @TableField("start_date_time")
    private Date startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "会议结束时间")
    @TableField("end_date_time")
    private Date endDateTime;

    @ApiModelProperty(value = "会议主题")
    @TableField("conference_theme")
    private String conferenceTheme;

    @ApiModelProperty(value = "所属会议室id")
    @TableField("room_id")
    private String roomId;

    @ApiModelProperty(value = "预定会议室名称")
    @TableField("room_name")
    private String roomName;

    @ApiModelProperty(value = "预定人")
    @TableField("reserved_name")
    private String reservedName;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
