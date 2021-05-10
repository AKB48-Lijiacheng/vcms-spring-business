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
 * 会议室信息表
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ConferenceRoom查询对象", description="会议室信息表查询对象")
public class ConferenceRoomQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "所属位置id")
    @QueryCondition
    private String positionId;

    @ApiModelProperty(value = "所属位置名称")
    @QueryCondition
    private String positionName;

    @ApiModelProperty(value = "可容纳人数")
    @QueryCondition
    private Integer capacityNum;

    @ApiModelProperty(value = "简介信息")
    @QueryCondition
    private String explainInfo;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
