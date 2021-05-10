package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenInfoGroupQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.westcatr.vcms.service.ScreenInfoGroupService;
import com.westcatr.vcms.entity.ScreenInfoGroup;
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
 *  @description : ScreenInfoGroup 控制器
 *  ---------------------------------
 *   @author admin
 *  @since 2020-06-19
 */

@Api(value="ScreenInfoGroup接口[screenInfoGroup]", tags="接口")
@Slf4j
@RestController
@RequestMapping("//screenInfoGroup")
public class ScreenInfoGroupController {

    @Autowired
    private ScreenInfoGroupService screenInfoGroupService;

    /**
     * @description : 获取分页列表
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-19
     */
    @SaveLog(value="分页数据接口", module="管理")
    @IPermissions(value="screenInfoGroup:page")
    @ApiOperationSupport(order=1)
    @ApiOperation(value="分页数据接口[screenInfoGroup:page]", nickname="screenInfoGroup:page")
    @GetMapping("/page")
    public IResult<IPage<ScreenInfoGroup>> getScreenInfoGroupPage(ScreenInfoGroupQuery query) {
        return IResult.ok(screenInfoGroupService.ipage(query));
    }

    /**
     * @description : 通过id获取
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-19
     */
    @SaveLog(value="获取数据接口", module="管理")
    @IPermissions(value="screenInfoGroup:get")
    @ApiOperationSupport(order=2)
    @ApiOperation(value="获取数据接口[screenInfoGroup:get]", nickname="screenInfoGroup:get")
    @GetMapping("/get")
    public IResult<ScreenInfoGroup> getScreenInfoGroupById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(screenInfoGroupService.igetById(id));
    }

    /**
     * @description : 新增
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-19
     */
    @SaveLog(value="新增数据接口", level = 2, module="管理")
    @IPermissions(value="screenInfoGroup:add")
    @ApiOperationSupport(order=3)
    @ApiOperation(value="新增数据接口[screenInfoGroup:add]", nickname="screenInfoGroup:add")
    @PostMapping("/add")
    public IResult addScreenInfoGroup(@RequestBody @Valid ScreenInfoGroup param) {
        return IResult.auto(screenInfoGroupService.isave(param));
    }

    /**
     * @description : 更新
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-19
     */
    @SaveLog(value="更新数据接口", level = 2, module="管理")
    @IPermissions(value="screenInfoGroup:update")
    @ApiOperationSupport(order=4)
    @ApiOperation(value="更新数据接口[screenInfoGroup:update]", nickname="screenInfoGroup:update")
    @PostMapping("/update")
    public IResult updateScreenInfoGroupById(@RequestBody @Valid ScreenInfoGroup param) {
        return IResult.auto(screenInfoGroupService.iupdate(param));
    }

    /**
     * @description : 通过id删除
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-19
     */
    @SaveLog(value="删除数据接口", level = 3, module="管理")
    @IPermissions(value="screenInfoGroup:del")
    @ApiOperationSupport(order=5)
    @ApiOperation(value="删除数据接口[screenInfoGroup:del]", nickname="screenInfoGroup:del")
    @DeleteMapping("/delete")
    public IResult deleteScreenInfoGroupById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            screenInfoGroupService.iremove(s);
        }
        return IResult.ok();
    }

}