package com.westcatr.vcms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenQuery;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;

import cn.hutool.core.lang.Dict;

import org.springframework.beans.factory.annotation.Autowired;

import com.westcatr.vcms.service.ScreenDeviceOnlineService;
import com.westcatr.vcms.service.ScreenService;
import com.westcatr.vcms.entity.Screen;
import com.westcatr.vcms.entity.ScreenDeviceOnline;
import com.westcatr.vcms.param.ScreenDeviceVo;
import com.westcatr.vcms.param.UpdateOnlineParam;
import com.westcatr.rd.base.acommon.annotation.IPermissions;
import com.westcatr.rd.base.acommon.annotation.SaveLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.westcatr.rd.base.acommon.vo.IResult;
import com.westcatr.rd.base.bweb.util.IpUtil;

import lombok.extern.slf4j.Slf4j;

import static cn.hutool.core.util.StrUtil.COMMA;

/**
 * @description : Screen 控制器 ---------------------------------
 * @author admin
 * @since 2020-06-10
 */

@Api(value = "Screen接口[screen]", tags = "屏幕管理接口")
@Slf4j
@RestController
@RequestMapping("/screen")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @Autowired
    private ScreenDeviceOnlineService screenDeviceOnlineService;

    /**
     * @description : 获取分页列表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-10
     */
    @SaveLog(value = "屏幕管理分页数据接口", module = "屏幕管理管理")
    @IPermissions(value = "screen:page")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "屏幕管理分页数据接口[screen:page]", nickname = "screen:page")
    @GetMapping("/page")
    public IResult<IPage<Screen>> getScreenPage(ScreenQuery query) {
        return IResult.ok(screenService.ipage(query));
    }

    /**
     * @description : 通过id获取屏幕管理 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-10
     */
    @SaveLog(value = "获取屏幕管理数据接口", module = "屏幕管理管理")
    @IPermissions(value = "screen:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取屏幕管理数据接口[screen:get]", nickname = "screen:get")
    @GetMapping("/get")
    public IResult<Screen> getScreenById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(screenService.igetById(id));
    }

    /**
     * @description : 新增屏幕管理 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-10
     */
    @SaveLog(value = "新增屏幕管理数据接口", level = 2, module = "屏幕管理管理")
    @IPermissions(value = "screen:add")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增屏幕管理数据接口[screen:add]", nickname = "screen:add")
    @PostMapping("/add")
    public IResult addScreen(@RequestBody @Valid Screen param) {
        return IResult.auto(screenService.isave(param));
    }

    /**
     * @description : 更新屏幕管理 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-10
     */
    @SaveLog(value = "更新屏幕管理数据接口", level = 2, module = "屏幕管理管理")
    @IPermissions(value = "screen:update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新屏幕管理数据接口[screen:update]", nickname = "screen:update")
    @PostMapping("/update")
    public IResult updateScreenById(@RequestBody @Valid Screen param) {
        return IResult.auto(screenService.iupdate(param));
    }

    /**
     * @description : 通过id删除屏幕管理 ---------------------------------
     * @author : admin
     * @since : Create in 2020-06-10
     */
    @SaveLog(value = "删除屏幕管理数据接口", level = 3, module = "屏幕管理管理")
    @IPermissions(value = "screen:del")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除屏幕管理数据接口[screen:del]", nickname = "screen:del")
    @DeleteMapping("/delete")
    public IResult deleteScreenById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            screenService.iremove(s);
        }
        return IResult.ok();
    }

    @SaveLog(value = "更新屏幕设备的在线状态", level = 3, module = "屏幕管理管理")
    @IPermissions(value = "screen:online")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "更新屏幕设备的在线状态接口[screen:updateOnline]", nickname = "screen:updateOnline")
    @PostMapping("/updateOnline")
    public IResult updateOnline(@RequestBody UpdateOnlineParam updateOnline) {
        String ip = IpUtil.getIpAddr();
        ScreenDeviceOnline screenDeviceOnline = new ScreenDeviceOnline();
        screenDeviceOnline.setDeviceIp(ip);
        screenDeviceOnline.setExt(updateOnline.getExt());
        screenDeviceOnline.setScreenId(updateOnline.getScreenId());
        screenDeviceOnlineService.isave(screenDeviceOnline);
        return IResult.ok();
    }

    @SaveLog(value = "查询当前设备绑定的屏幕在线数量", level = 3, module = "屏幕管理管理")
    @IPermissions(value = "screen:online")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "查询当前设备绑定的屏幕在线数量接口[screen:online]", nickname = "screen:online")
    @GetMapping("/online")
    public IResult<Dict> online(@NotNull(message = "屏幕id不能为空") @RequestParam(value = "screenId") String screenId) {
        return IResult.ok(screenDeviceOnlineService.onlineCount(screenId));
    }

    @SaveLog(value = "查询当前屏幕连接设备的历史记录", level = 3, module = "屏幕管理管理")
    @IPermissions(value = "screen:onlineHistory")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "查询当前屏幕连接设备的历史记录接口[screen:onlineHistory]", nickname = "screen:onlineHistory")
    @GetMapping("/onlineHistory")
    public IResult<List<ScreenDeviceVo>> onlineHistory(
            @NotNull(message = "屏幕id不能为空") @RequestParam(value = "screenId") String screenId, @RequestParam(value = "tfOnlineDevice")boolean tfOnlineDevice) {
        return IResult.ok(screenDeviceOnlineService.getScreenDeviceVo(screenId,tfOnlineDevice));
    }
}