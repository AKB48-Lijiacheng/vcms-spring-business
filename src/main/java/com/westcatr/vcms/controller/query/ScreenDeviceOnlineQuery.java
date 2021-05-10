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
 * 使用屏幕在线设备表
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ScreenDeviceOnline查询对象", description="使用屏幕在线设备表查询对象")
public class ScreenDeviceOnlineQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @QueryCondition
    private String screenId;

    @ApiModelProperty(value = "设备ip")
    @QueryCondition
    private String deviceIp;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
