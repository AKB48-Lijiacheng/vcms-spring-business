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
 * 屏幕管理
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Screen查询对象", description="屏幕管理查询对象")
public class ScreenQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "屏幕名称")
    @QueryCondition
    private String screenName;

    @ApiModelProperty(value = "播放资源类型")
    @QueryCondition
    private String resourceType;

    @ApiModelProperty(value = "播放资源名称")
    @QueryCondition
    private String resourceName;

    @ApiModelProperty(value = "播放资源id")
    @QueryCondition
    private String resourceId;

    @ApiModelProperty(value = "0是离线，1是在线")
    @QueryCondition
    private Integer status;

    @ApiModelProperty(value = "备注")
    @QueryCondition
    private String remark;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
