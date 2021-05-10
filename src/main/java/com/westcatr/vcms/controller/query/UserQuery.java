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
 * 系统用户
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User查询对象", description="系统用户查询对象")
public class UserQuery extends TimeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @QueryCondition
    private String id;

    @ApiModelProperty(value = "账号")
    @QueryCondition
    private String username;

    @ApiModelProperty(value = "密码")
    @QueryCondition
    private String password;

    @ApiModelProperty(value = "手机号")
    @QueryCondition
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    @QueryCondition
    private String fullName;

    @ApiModelProperty(value = "部门id")
    @QueryCondition
    private Integer departmentId;

    @ApiModelProperty(value = "状态，1正常，0禁用")
    @QueryCondition
    private Integer enable;

    @ApiModelProperty(value = "注册时间")
    @QueryCondition
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @QueryCondition
    private Date updateTime;
}
