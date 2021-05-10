package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenInfoQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.westcatr.vcms.service.ScreenInfoService;
import com.westcatr.vcms.entity.ScreenInfo;
import com.westcatr.vcms.param.ArrayParam;
import com.westcatr.vcms.param.ScreenUpdateParam;
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
 * @description : ScreenInfo 控制器 ---------------------------------
 * @author admin
 * @since 2020-06-18
 */

@Api(value = "ScreenInfo接口[screenInfo]", tags = "屏幕信息表接口")
@Slf4j
@RestController
@RequestMapping("/screenInfo")
public class ScreenInfoController {

    @Autowired
    private ScreenInfoService screenInfoService;

    /**
     * @description : 获取分页列表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-18
     */
    @SaveLog(value = "屏幕信息表分页数据接口", module = "屏幕信息表管理")
    @IPermissions(value = "screenInfo:page")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "屏幕信息表分页数据接口[screenInfo:page]", nickname = "screenInfo:page")
    @GetMapping("/page")
    public IResult<IPage<ScreenInfo>> getScreenInfoPage(ScreenInfoQuery query) {
        return IResult.ok(screenInfoService.ipage(query));
    }

    /**
     * @description : 通过id获取屏幕信息表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-18
     */
    @SaveLog(value = "获取屏幕信息表数据接口", module = "屏幕信息表管理")
    @IPermissions(value = "screenInfo:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取屏幕信息表数据接口[screenInfo:get]", nickname = "screenInfo:get")
    @GetMapping("/get")
    public IResult<ScreenInfo> getScreenInfoById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(screenInfoService.igetById(id));
    }

    /**
     * @description : 新增屏幕信息表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-18
     */
    @SaveLog(value = "新增屏幕信息表数据接口", level = 2, module = "屏幕信息表管理")
    @IPermissions(value = "screenInfo:add")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增屏幕信息表数据接口[screenInfo:add]", nickname = "screenInfo:add")
    @PostMapping("/add")
    public IResult addScreenInfo(@RequestBody @Valid ScreenInfo param) {
        return IResult.auto(screenInfoService.isave(param));
    }

    /**
     * @description : 更新屏幕信息表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-18
     */
    @SaveLog(value = "更新屏幕信息表数据接口", level = 2, module = "屏幕信息表管理")
    @IPermissions(value = "screenInfo:update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新屏幕信息表数据接口[screenInfo:update]", nickname = "screenInfo:update")
    @PostMapping("/update")
    public IResult updateScreenInfoById(@RequestBody @Valid ScreenInfo param) {
        return IResult.auto(screenInfoService.iupdate(param));
    }

    /**
     * @description : 通过id删除屏幕信息表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-18
     */
    @SaveLog(value = "删除屏幕信息表数据接口", level = 3, module = "屏幕信息表管理")
    @IPermissions(value = "screenInfo:del")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除屏幕信息表数据接口[screenInfo:del]", nickname = "screenInfo:del")
    @DeleteMapping("/delete")
    public IResult deleteScreenInfoById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            screenInfoService.iremove(s);
        }
        return IResult.ok();
    }

    @SaveLog(value = "更新屏幕在线接口", level = 2, module = "屏幕信息表管理")
    // @IPermissions(value = "screenInfo:updateOnline")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新屏幕在线接口接口[screenInfo:updateOnline]", nickname = "screenInfo:updateOnline")
    @PostMapping("/updateOnline")
    public IResult<ScreenInfo> updateOnline(@RequestBody @Valid ScreenInfo param) {
        ScreenInfo screenInfo=screenInfoService.isaveOrUpdate(param);
        return IResult.ok(screenInfo);
    }

    @SaveLog(value = "批量更新屏幕信息表数据接口", level = 2, module = "屏幕信息表管理")
    @IPermissions(value = "screenInfo:batchUpdate")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "批量更新屏幕信息表数据接口[screenInfo:batchUpdate]", nickname = "screenInfo:batchUpdate")
    @PostMapping("/batchUpdate")
    public IResult batchUpdate(@RequestBody ScreenUpdateParam param) {
        return IResult.auto(screenInfoService.iBatchUpdate(param));
    }

    @SaveLog(value = "根据ids返回数据接口", module = "屏幕信息表管理")
    @IPermissions(value = "screenInfo:listByIds")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "根据ids返回数据数据接口[screenInfo:listByIds]", nickname = "screenInfo:listByIds")
    @PostMapping("/listByIds")
    public IResult<List<ScreenInfo>> listByIds(@RequestBody ArrayParam ArrayParam) {
        return IResult.ok(screenInfoService.listByIds(ArrayParam.getData()));
    }
}