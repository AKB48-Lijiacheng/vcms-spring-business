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
 * 使用屏幕在线设备表
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_screen_device_online")
@ApiModel(value="ScreenDeviceOnline对象", description="使用屏幕在线设备表")
public class ScreenDeviceOnline extends Model<ScreenDeviceOnline> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("screen_id")
    private String screenId;

    @ApiModelProperty(value = "设备ip")
    @TableField("device_ip")
    private String deviceIp;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("remake")
    private String remake;

    @TableField("ext")
    private String ext;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
