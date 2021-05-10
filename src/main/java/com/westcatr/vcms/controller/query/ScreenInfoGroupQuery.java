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
 * 
 * </p>
 *
 * @author admin
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ScreenInfoGroup查询对象", description="查询对象")
public class ScreenInfoGroupQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "分组名称")
    @QueryCondition
    private String groupName;

    @ApiModelProperty(value = "分组说明")
    @QueryCondition
    private String groupReamke;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
