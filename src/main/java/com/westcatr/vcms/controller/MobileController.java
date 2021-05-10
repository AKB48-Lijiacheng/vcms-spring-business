package com.westcatr.vcms.controller;

import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.westcatr.rd.base.acommon.annotation.SaveLog;
import com.westcatr.rd.base.acommon.vo.IResult;
import com.westcatr.vcms.service.MobileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "安卓客户端接口[area]", tags = "移动端接口")
@RestController
@RequestMapping("/mobile")
public class MobileController {

    @Value("${title.context}")
    private String titleContext;

    @Autowired
    private MobileService mobileService;

    @SaveLog(value = "设备注册接口", module = "移动端接口")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "设备注册接口", notes = "mobiles:registerDevice")
    @GetMapping("/registerDevice")
    public IResult<String> registerDevice(
            @NotNull(message = "设备mac地址不能为空") @RequestParam(value = "macId") String macId) {
        return IResult.ok(mobileService.registerDevice(macId));
    }

    @SaveLog(value = "欢迎页信息接口", module = "移动端接口")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "欢迎页信息接口", notes = "mobiles:welcomeInfo")
    @GetMapping("/welcomeInfo")
    public IResult<String> welcomeInfo() throws Exception {
        String returnVal = new String(titleContext.getBytes("ISO-8859-1"), "UTF-8");
        return IResult.ok("成功!",returnVal);
    }
}
