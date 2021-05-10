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
 * 富文本存储表
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FileText查询对象", description="富文本存储表查询对象")
public class FileTextQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "富文本主体")
    @QueryCondition
    private String contentInfo;

    @ApiModelProperty(value = "创建人")
    @QueryCondition
    private String userName;

    @ApiModelProperty(value = "分组id")
    @QueryCondition
    private Integer groupId;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
