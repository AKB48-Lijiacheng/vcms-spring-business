package com.westcatr.vcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 屏幕信息表
 * </p>
 *
 * @author admin
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_screen_info")
@ApiModel(value = "ScreenInfo对象", description = "屏幕信息表")
public class ScreenInfo extends Model<ScreenInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "系统类型")
    @TableField("system_type")
    private String systemType;

    @ApiModelProperty(value = "手机平台的系统版本")
    @TableField("system_version")
    private String systemVersion;

    @ApiModelProperty(value = "设备唯一id")
    @TableField("device_id")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    @TableField("device_name")
    private String deviceName;

    @ApiModelProperty(value = "屏幕分辨率宽")
    @TableField("screen_width")
    private Integer screenWidth;

    @ApiModelProperty(value = "屏幕分辨率高")
    @TableField("screen_height")
    private Integer screenHeight;

    @ApiModelProperty(value = "在线状态 0不在线，1在线")
    @TableField("online")
    private Integer online;

    @ApiModelProperty(value = "扩展字段")
    @TableField("ext")
    private String ext;

    @ApiModelProperty(value = "最后在线时间")
    @TableField("last_online")
    private Date lastOnline;

    @ApiModelProperty(value = "是否为控制端")
    @TableField(exist = false)
    private boolean tfControlEnd = false;

    @ApiModelProperty(value = "所属分组id")
    @TableField("group_id")
    private String groupId;

    @ApiModelProperty(value = "播放资源类型")
    @TableField(value = "resource_type"/* , updateStrategy = FieldStrategy.IGNORED */)
    private String resourceType;

    @ApiModelProperty(value = "播放资源名称")
    @TableField(value = "resource_name"/* , updateStrategy = FieldStrategy.IGNORED */)
    private String resourceName;

    @ApiModelProperty(value = "播放资源id")
    @TableField(value = "resource_id"/* , updateStrategy = FieldStrategy.IGNORED */)
    private String resourceId;

    @ApiModelProperty(value = "设备是否在线")
    @TableField(exist = false)
    private Integer tfOnline;

    @ApiModelProperty(value = "离线时长")
    @TableField(exist = false)
    private Long offlineDuration;

    @ApiModelProperty(value = "源文件")
    @TableField(exist = false)
    private File file;

    @ApiModelProperty(value = "播放库")
    @TableField(exist = false)
    private FileGroup fileGroup;

    @TableField("action")
    private String action;

    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "所属区域id")
    @TableField("area_id")
    private String areaId;

    @ApiModelProperty(value = "所属区域名称")
    @TableField("area_name")
    private String areaName;

    @ApiModelProperty(value = "单文件时间间隔")
    @TableField(exist = false)
    private Integer intervalTime;

   @ApiModelProperty(value = "单文件时间间隔")
   @TableField("play_index")
   private Integer playIndex;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
