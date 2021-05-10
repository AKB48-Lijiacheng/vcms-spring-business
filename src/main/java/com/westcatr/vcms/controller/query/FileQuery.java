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
 * 上传资源表
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="File查询对象", description="上传资源表查询对象")
public class FileQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "文件名称")
    @QueryCondition
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @QueryCondition
    private String fileType;

    @ApiModelProperty(value = "文件路径")
    @QueryCondition
    private String filePath;

    @ApiModelProperty(value = "文件大小（kb）")
    @QueryCondition
    private Integer fileSize;

    @ApiModelProperty(value = "备注")
    @QueryCondition
    private String remarkInfo;

    @ApiModelProperty(value = "分组id")
    @QueryCondition
    private String groupId;

    @ApiModelProperty(value = "创建人名称")
    @QueryCondition
    private String userName;

    @QueryCondition()
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
