package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ConferenceRoomReservationQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.westcatr.vcms.service.ConferenceRoomReservationService;
import com.westcatr.vcms.entity.ConferenceRoomReservation;
import com.westcatr.vcms.param.ConferenceRoomDateCount;
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
 * @description : ConferenceRoomReservation 控制器
 *              ---------------------------------
 * @author admin
 * @since 2020-05-27
 */

@Api(value = "ConferenceRoomReservation接口[conferenceRoomReservation]", tags = "会议室预定表接口")
@Slf4j
@RestController
@RequestMapping("/conferenceRoomReservation")
public class ConferenceRoomReservationController {

    @Autowired
    private ConferenceRoomReservationService conferenceRoomReservationService;

    /**
     * @description : 获取分页列表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "会议室预定表分页数据接口", module = "会议室预定表管理")
    @IPermissions(value = "conferenceRoomReservation:page")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "会议室预定表分页数据接口[conferenceRoomReservation:page]", nickname = "conferenceRoomReservation:page")
    @GetMapping("/page")
    public IResult<IPage<ConferenceRoomReservation>> getConferenceRoomReservationPage(
            ConferenceRoomReservationQuery query) {
        return IResult.ok(conferenceRoomReservationService.ipage(query));
    }

    /**
     * @description : 通过id获取会议室预定表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "获取会议室预定表数据接口", module = "会议室预定表管理")
    @IPermissions(value = "conferenceRoomReservation:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取会议室预定表数据接口[conferenceRoomReservation:get]", nickname = "conferenceRoomReservation:get")
    @GetMapping("/get")
    public IResult<ConferenceRoomReservation> getConferenceRoomReservationById(
            @NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(conferenceRoomReservationService.igetById(id));
    }

    /**
     * @description : 新增会议室预定表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "新增会议室预定表数据接口", level = 2, module = "会议室预定表管理")
    @IPermissions(value = "conferenceRoomReservation:add")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增会议室预定表数据接口[conferenceRoomReservation:add]", nickname = "conferenceRoomReservation:add")
    @PostMapping("/add")
    public IResult addConferenceRoomReservation(@RequestBody @Valid ConferenceRoomReservation param) {
        return IResult.auto(conferenceRoomReservationService.isave(param));
    }

    /**
     * @description : 更新会议室预定表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "更新会议室预定表数据接口", level = 2, module = "会议室预定表管理")
    @IPermissions(value = "conferenceRoomReservation:update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新会议室预定表数据接口[conferenceRoomReservation:update]", nickname = "conferenceRoomReservation:update")
    @PostMapping("/update")
    public IResult updateConferenceRoomReservationById(@RequestBody @Valid ConferenceRoomReservation param) {
        return IResult.auto(conferenceRoomReservationService.iupdate(param));
    }

    /**
     * @description : 通过id删除会议室预定表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-27
     */
    @SaveLog(value = "删除会议室预定表数据接口", level = 3, module = "会议室预定表管理")
    @IPermissions(value = "conferenceRoomReservation:del")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除会议室预定表数据接口[conferenceRoomReservation:del]", nickname = "conferenceRoomReservation:del")
    @DeleteMapping("/delete")
    public IResult deleteConferenceRoomReservationById(
            @NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            conferenceRoomReservationService.iremove(s);
        }
        return IResult.ok();
    }

    @SaveLog(value = "获取会议室预定的日期和数量接口", module = "会议室预定表管理")
    @IPermissions(value = "conferenceRoomReservation:schedulCount")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取会议室预定的日期和数量接口[conferenceRoomReservation:schedulCount]", nickname = "conferenceRoomReservation:schedulCount")
    @GetMapping("/schedulCount")
    public IResult<List<ConferenceRoomDateCount>> schedulCount() {
        return IResult.ok(conferenceRoomReservationService.schedulCount());
    }

}