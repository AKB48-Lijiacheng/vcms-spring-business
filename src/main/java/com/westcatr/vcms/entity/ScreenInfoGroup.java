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
 * 
 * </p>
 *
 * @author admin
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_screen_info_group")
@ApiModel(value="ScreenInfoGroup对象", description="")
public class ScreenInfoGroup extends Model<ScreenInfoGroup> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "分组名称")
    @TableField("group_name")
    private String groupName;

    @ApiModelProperty(value = "分组说明")
    @TableField("group_reamke")
    private String groupReamke;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "分组下面设备数量")
    @TableField(exist = false)
    private Integer deviceCount;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
