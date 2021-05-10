package com.westcatr.vcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.util.function.LongConsumer;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 上传资源表
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_file")
@ApiModel(value = "File对象", description = "上传资源表")
public class File extends Model<File> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "文件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @TableField("file_type")
    private String fileType;

    @ApiModelProperty(value = "文件路径")
    @TableField("file_path")
    private String filePath;

    @ApiModelProperty(value = "文件大小（kb）")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty(value = "备注")
    @TableField("remark_info")
    private String remarkInfo;

    @ApiModelProperty(value = "分组id")
    @TableField(value = "group_id", updateStrategy = FieldStrategy.IGNORED)
    private String groupId;

    @ApiModelProperty(value = "创建人名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "扩展属性")
    @TableField("extend_info")
    private String extendInfo;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "时间间隔")
    @TableField("interval_time")
    private Integer intervalTime;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
