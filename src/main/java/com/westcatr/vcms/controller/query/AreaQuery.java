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
 * 区域表
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Area查询对象", description="区域表查询对象")
public class AreaQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "位置名称")
    @QueryCondition
    private String areaName;

    @ApiModelProperty(value = "排序")
    @QueryCondition
    private Integer sort;

    @ApiModelProperty(value = "父级id")
    @QueryCondition
    private String pid;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
