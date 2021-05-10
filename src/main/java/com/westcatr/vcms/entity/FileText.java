package com.westcatr.vcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 富文本存储表
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vc_file_text")
@ApiModel(value="FileText对象", description="富文本存储表")
public class FileText extends Model<FileText> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "富文本主体")
    @TableField("content_info")
    private String contentInfo;

    @ApiModelProperty(value = "创建人")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "分组id")
    @TableField("group_id")
    private String groupId;

    @ApiModelProperty(value = "分组id")
    @TableField("sort")
    private Integer sort;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "扩展属性")
    @TableField("extend_info")
    private String extendInfo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
