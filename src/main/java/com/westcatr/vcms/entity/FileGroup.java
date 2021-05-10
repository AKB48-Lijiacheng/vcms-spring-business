package com.westcatr.vcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.westcatr.vcms.param.FileGroupAddParamChild;
import com.westcatr.vcms.param.FileGroupPreviewVo;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 播放集表
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_file_group")
@ApiModel(value="FileGroup对象", description="播放集表")
public class FileGroup extends Model<FileGroup> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "分组名称")
    @TableField("group_name")
    private String groupName;

    @ApiModelProperty(value = "备注说明")
    @TableField("remake_info")
    private String remakeInfo;

    @ApiModelProperty(value = "资源类型")
    @TableField("file_type")
    private String fileType;

    @ApiModelProperty(value = "间隔时间")
    @TableField("interval_num")
    private Integer intervalNum;

    @ApiModelProperty(value = "创建人")
    @TableField("user_name")
    private String userName;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "是否自动播放")
    @TableField("auto_display")
    private Integer autoDisplay;

    @ApiModelProperty(value = "播放间隔")
    @TableField("display_duration")
    private Integer displayDuration;


    @TableField(exist = false)
    private List<FileGroupPreviewVo> listFileGroupPreviewVo;

    @TableField(exist = false)
    private List<FileGroupAddParamChild> listFileGroupAddParamChild;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
