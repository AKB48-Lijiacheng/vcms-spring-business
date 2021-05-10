package com.westcatr.vcms.service.impl;

import com.westcatr.vcms.entity.ScreenInfo;
import com.westcatr.vcms.service.MobileService;
import com.westcatr.vcms.service.ScreenInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileServiceImpl implements MobileService {

    @Autowired
    private ScreenInfoService screenInfoService;

    @Override
    public String registerDevice(String deviceId) {
        ScreenInfo screenInfo = new ScreenInfo();
        screenInfo.setDeviceId(deviceId);
        return screenInfoService.isaveMobile(screenInfo);
    }
}
