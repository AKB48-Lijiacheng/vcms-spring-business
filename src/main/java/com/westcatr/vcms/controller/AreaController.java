package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.AreaQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.westcatr.vcms.service.AreaService;
import com.westcatr.vcms.entity.Area;
import com.westcatr.vcms.param.AreaVo;
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
 * @description : Area 控制器 ---------------------------------
 * @author admin
 * @since 2020-05-27
 */

@Api(value = "Area接口[area]", tags = "区域表接口")
@Slf4j
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * @description : 获取分页列表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "区域表分页数据接口", module = "区域表管理")
    @IPermissions(value = "area:page")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "区域表分页数据接口[area:page]", nickname = "area:page")
    @GetMapping("/page")
    public IResult<IPage<AreaVo>> getAreaPage(AreaQuery query) {
        return IResult.ok(areaService.ipage(query));
    }

    /**
     * @description : 通过id获取区域表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "获取区域表数据接口", module = "区域表管理")
    @IPermissions(value = "area:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取区域表数据接口[area:get]", nickname = "area:get")
    @GetMapping("/get")
    public IResult<AreaVo> getAreaById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(areaService.igetById(id));
    }

    /**
     * @description : 新增区域表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "新增区域表数据接口", level = 2, module = "区域表管理")
    @IPermissions(value = "area:add")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增区域表数据接口[area:add]", nickname = "area:add")
    @PostMapping("/add")
    public IResult addArea(@RequestBody @Valid Area param) {
        return IResult.auto(areaService.isave(param));
    }

    /**
     * @description : 更新区域表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "更新区域表数据接口", level = 2, module = "区域表管理")
    @IPermissions(value = "area:update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新区域表数据接口[area:update]", nickname = "area:update")
    @PostMapping("/update")
    public IResult updateAreaById(@RequestBody @Valid Area param) {
        return IResult.auto(areaService.iupdate(param));
    }

    /**
     * @description : 通过id删除区域表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "删除区域表数据接口", level = 3, module = "区域表管理")
    @IPermissions(value = "area:del")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除区域表数据接口[area:del]", nickname = "area:del")
    @DeleteMapping("/delete")
    public IResult deleteAreaById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            areaService.iremove(s);
        }
        return IResult.ok();
    }

    /**
     * @description : 通过id获取区域表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "获取区域表树形接口", module = "区域表管理")
    @IPermissions(value = "area:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取区域表树形接口[area:tree]", nickname = "area: ")
    @GetMapping("/tree")
    public IResult<List<AreaVo>> getTree() {
        return IResult.ok(areaService.treeInfo());
    }
}