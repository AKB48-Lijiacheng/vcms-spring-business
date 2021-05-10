package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.UserQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.westcatr.vcms.service.UserService;
import com.westcatr.vcms.entity.User;
import com.westcatr.rd.base.acommon.annotation.IPermissions;
import com.westcatr.rd.base.acommon.annotation.SaveLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.westcatr.rd.base.acommon.vo.IResult;
import lombok.extern.slf4j.Slf4j;

import static cn.hutool.core.util.StrUtil.COMMA;
/**
 *  @description : User 控制器
 *  ---------------------------------
 *   @author admin
 *  @since 2020-05-27
 */

@Api(value="User接口[user]", tags="系统用户接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @description : 获取分页列表
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="系统用户分页数据接口", module="系统用户管理")
    @IPermissions(value="user:page")
    @ApiOperationSupport(order=1)
    @ApiOperation(value="系统用户分页数据接口[user:page]", nickname="user:page")
    @GetMapping("/page")
    public IResult<IPage<User>> getUserPage(UserQuery query) {
        return IResult.ok(userService.ipage(query));
    }

    /**
     * @description : 通过id获取系统用户
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="获取系统用户数据接口", module="系统用户管理")
    @IPermissions(value="user:get")
    @ApiOperationSupport(order=2)
    @ApiOperation(value="获取系统用户数据接口[user:get]", nickname="user:get")
    @GetMapping("/get")
    public IResult<User> getUserById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(userService.igetById(id));
    }

    /**
     * @description : 新增系统用户
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="新增系统用户数据接口", level = 2, module="系统用户管理")
    @IPermissions(value="user:add")
    @ApiOperationSupport(order=3)
    @ApiOperation(value="新增系统用户数据接口[user:add]", nickname="user:add")
    @PostMapping("/add")
    public IResult addUser(@RequestBody @Valid User param) {
        return IResult.auto(userService.isave(param));
    }

    /**
     * @description : 更新系统用户
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="更新系统用户数据接口", level = 2, module="系统用户管理")
    @IPermissions(value="user:update")
    @ApiOperationSupport(order=4)
    @ApiOperation(value="更新系统用户数据接口[user:update]", nickname="user:update")
    @PostMapping("/update")
    public IResult updateUserById(@RequestBody @Valid User param) {
        return IResult.auto(userService.iupdate(param));
    }

    /**
     * @description : 通过id删除系统用户
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="删除系统用户数据接口", level = 3, module="系统用户管理")
    @IPermissions(value="user:del")
    @ApiOperationSupport(order=5)
    @ApiOperation(value="删除系统用户数据接口[user:del]", nickname="user:del")
    @DeleteMapping("/delete")
    public IResult deleteUserById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            userService.iremove(s);
        }
        return IResult.ok();
    }

}