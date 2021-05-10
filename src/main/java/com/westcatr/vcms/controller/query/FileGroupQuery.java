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
 * 播放集表
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FileGroup查询对象", description="播放集表查询对象")
public class FileGroupQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "分组名称")
    @QueryCondition
    private String groupName;

    @ApiModelProperty(value = "备注说明")
    @QueryCondition
    private String remakeInfo;

    @ApiModelProperty(value = "资源类型")
    @QueryCondition
    private String fileType;

    @ApiModelProperty(value = "间隔时间")
    @QueryCondition
    private Integer intervalNum;

    @ApiModelProperty(value = "创建人")
    @QueryCondition
    private String userName;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
