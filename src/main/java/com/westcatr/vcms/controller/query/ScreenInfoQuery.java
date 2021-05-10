package com.westcatr.vcms.controller.query;

import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.QueryCondition;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.QueryCondition.Condition;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.QueryCondition.Sort;
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
 * 屏幕信息表
 * </p>
 *
 * @author admin
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ScreenInfo查询对象", description="屏幕信息表查询对象")
public class ScreenInfoQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "系统类型")
    @QueryCondition
    private String systemType;

    @ApiModelProperty(value = "手机平台的系统版本")
    @QueryCondition
    private String systemVersion;

    @ApiModelProperty(value = "设备唯一id")
    @QueryCondition
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    @QueryCondition
    private String deviceName;

    @ApiModelProperty(value = "屏幕分辨率宽")
    @QueryCondition
    private Integer screenWidth;

    @ApiModelProperty(value = "屏幕分辨率高")
    @QueryCondition
    private Integer screenHeight;

    @ApiModelProperty(value = "扩展字段")
    @QueryCondition
    private String ext;

    @ApiModelProperty(value = "分组id")
    @QueryCondition
    private String groupId;

    @ApiModelProperty(value = "排序")
    @QueryCondition(sort = Sort.AUTO,condition = Condition.DEFAULT)
    private Integer sort;

    @QueryCondition
    private Date createTime;

    @QueryCondition
    private Date updateTime;
}
