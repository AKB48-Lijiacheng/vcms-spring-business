package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.FileGroupQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.westcatr.vcms.service.FileGroupService;
import com.westcatr.vcms.entity.FileGroup;
import com.westcatr.vcms.param.FileGroupAddParam;
import com.westcatr.vcms.param.FileGroupPreviewVo;
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
 * @description : FileGroup 控制器 ---------------------------------
 * @author admin
 * @since 2020-05-15
 */

@Api(value = "FileGroup接口[fileGroup]", tags = "播放集表接口")
@Slf4j
@RestController
@RequestMapping("/fileGroup")
public class FileGroupController {

    @Autowired
    private FileGroupService fileGroupService;

    /**
     * @description : 获取分页列表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "播放集表分页数据接口", module = "播放集表管理")
    @IPermissions(value = "fileGroup:page")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "播放集表分页数据接口[fileGroup:page]", nickname = "fileGroup:page")
    @GetMapping("/page")
    public IResult<IPage<FileGroup>> getFileGroupPage(FileGroupQuery query) {
        return IResult.ok(fileGroupService.ipage(query));
    }

    /**
     * @description : 通过id获取播放集表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "获取播放集表数据接口", module = "播放集表管理")
    @IPermissions(value = "fileGroup:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取播放集表数据接口[fileGroup:get]", nickname = "fileGroup:get")
    @GetMapping("/get")
    public IResult<FileGroup> getFileGroupById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(fileGroupService.igetById(id));
    }

    /**
     * @description : 新增播放集表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "新增播放集表数据接口", level = 2, module = "播放集表管理")
    @IPermissions(value = "fileGroup:add")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增播放集表数据接口[fileGroup:add]", nickname = "fileGroup:add")
    @PostMapping("/add")
    public IResult addFileGroup(@RequestBody @Valid FileGroupAddParam param) {
        return IResult.auto(fileGroupService.isave(param));
    }

    /**
     * @description : 更新播放集表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "更新播放集表数据接口", level = 2, module = "播放集表管理")
    @IPermissions(value = "fileGroup:update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新播放集表数据接口[fileGroup:update]", nickname = "fileGroup:update")
    @PostMapping("/update")
    public IResult updateFileGroupById(@RequestBody @Valid FileGroupAddParam param) {
        return IResult.auto(fileGroupService.iupdate(param));
    }

    /**
     * @description : 通过id删除播放集表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "删除播放集表数据接口", level = 3, module = "播放集表管理")
    @IPermissions(value = "fileGroup:del")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除播放集表数据接口[fileGroup:del]", nickname = "fileGroup:del")
    @DeleteMapping("/delete")
    public IResult deleteFileGroupById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            fileGroupService.iremove(s);
        }
        return IResult.ok();
    }

}