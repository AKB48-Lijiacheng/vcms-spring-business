package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ConferenceRoomQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.westcatr.vcms.service.ConferenceRoomService;
import com.westcatr.vcms.entity.ConferenceRoom;
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
 *  @description : ConferenceRoom 控制器
 *  ---------------------------------
 *   @author admin
 *  @since 2020-05-27
 */

@Api(value="ConferenceRoom接口[conferenceRoom]", tags="会议室信息表接口")
@Slf4j
@RestController
@RequestMapping("/conferenceRoom")
public class ConferenceRoomController {

    @Autowired
    private ConferenceRoomService conferenceRoomService;

    /**
     * @description : 获取分页列表
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="会议室信息表分页数据接口", module="会议室信息表管理")
    @IPermissions(value="conferenceRoom:page")
    @ApiOperationSupport(order=1)
    @ApiOperation(value="会议室信息表分页数据接口[conferenceRoom:page]", nickname="conferenceRoom:page")
    @GetMapping("/page")
    public IResult<IPage<ConferenceRoom>> getConferenceRoomPage(ConferenceRoomQuery query) {
        return IResult.ok(conferenceRoomService.ipage(query));
    }

    /**
     * @description : 通过id获取会议室信息表
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="获取会议室信息表数据接口", module="会议室信息表管理")
    @IPermissions(value="conferenceRoom:get")
    @ApiOperationSupport(order=2)
    @ApiOperation(value="获取会议室信息表数据接口[conferenceRoom:get]", nickname="conferenceRoom:get")
    @GetMapping("/get")
    public IResult<ConferenceRoom> getConferenceRoomById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(conferenceRoomService.igetById(id));
    }

    /**
     * @description : 新增会议室信息表
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="新增会议室信息表数据接口", level = 2, module="会议室信息表管理")
    @IPermissions(value="conferenceRoom:add")
    @ApiOperationSupport(order=3)
    @ApiOperation(value="新增会议室信息表数据接口[conferenceRoom:add]", nickname="conferenceRoom:add")
    @PostMapping("/add")
    public IResult addConferenceRoom(@RequestBody @Valid ConferenceRoom param) {
        return IResult.auto(conferenceRoomService.isave(param));
    }

    /**
     * @description : 更新会议室信息表
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="更新会议室信息表数据接口", level = 2, module="会议室信息表管理")
    @IPermissions(value="conferenceRoom:update")
    @ApiOperationSupport(order=4)
    @ApiOperation(value="更新会议室信息表数据接口[conferenceRoom:update]", nickname="conferenceRoom:update")
    @PostMapping("/update")
    public IResult updateConferenceRoomById(@RequestBody @Valid ConferenceRoom param) {
        return IResult.auto(conferenceRoomService.iupdate(param));
    }

    /**
     * @description : 通过id删除会议室信息表
     * ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value="删除会议室信息表数据接口", level = 3, module="会议室信息表管理")
    @IPermissions(value="conferenceRoom:del")
    @ApiOperationSupport(order=5)
    @ApiOperation(value="删除会议室信息表数据接口[conferenceRoom:del]", nickname="conferenceRoom:del")
    @DeleteMapping("/delete")
    public IResult deleteConferenceRoomById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            conferenceRoomService.iremove(s);
        }
        return IResult.ok();
    }

}