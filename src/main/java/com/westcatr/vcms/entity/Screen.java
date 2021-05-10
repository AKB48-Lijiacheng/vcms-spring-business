package com.westcatr.vcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.util.List;

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
 * 屏幕管理
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_screen")
@ApiModel(value="Screen对象", description="屏幕管理")
public class Screen extends Model<Screen> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "屏幕名称")
    @TableField("screen_name")
    private String screenName;

    @ApiModelProperty(value = "播放资源类型")
    @TableField("resource_type")
    private String resourceType;

    @ApiModelProperty(value = "播放资源名称")
    @TableField("resource_name")
    private String resourceName;

    @ApiModelProperty(value = "播放资源id")
    @TableField("resource_id")
    private String resourceId;

    @ApiModelProperty(value = "0是离线，1是在线")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "新增或者更新与字典的关系时候传入")
    @TableField(exist = false)
    private List<ScreenDic> screenDics;

    @ApiModelProperty(value = "在线设备数量")
    @TableField(exist = false)
    private Integer onlineCount;

    @ApiModelProperty(value = "离线设备数量")
    @TableField(exist = false)
    private Integer offlineCount;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
