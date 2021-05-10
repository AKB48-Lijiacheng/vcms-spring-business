package com.westcatr.vcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * 会议室信息表
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_conference_room")
@ApiModel(value="ConferenceRoom对象", description="会议室信息表")
public class ConferenceRoom extends Model<ConferenceRoom> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "会议室名称")
    @TableField("room_name")
    private String roomName;

    @ApiModelProperty(value = "所属位置id")
    @TableField("position_id")
    private String positionId;

    @ApiModelProperty(value = "所属位置名称")
    @TableField("position_name")
    private String positionName;

    @ApiModelProperty(value = "可容纳人数")
    @TableField("capacity_num")
    private Integer capacityNum;

    @ApiModelProperty(value = "简介信息")
    @TableField("explain_info")
    private String explainInfo;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
