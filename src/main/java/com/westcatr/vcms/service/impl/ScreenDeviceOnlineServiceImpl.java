package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenDeviceOnlineQuery;
import com.westcatr.vcms.entity.ScreenDeviceOnline;
import com.westcatr.vcms.mapper.ScreenDeviceOnlineMapper;
import com.westcatr.vcms.param.ScreenDeviceVo;
import com.westcatr.vcms.service.ScreenDeviceOnlineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 使用屏幕在线设备表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
@Service
public class ScreenDeviceOnlineServiceImpl extends ServiceImpl<ScreenDeviceOnlineMapper, ScreenDeviceOnline>
        implements ScreenDeviceOnlineService {

    @Override
    public IPage<ScreenDeviceOnline> ipage(ScreenDeviceOnlineQuery query) {
        return this.page(query.page(), new WrapperFactory<ScreenDeviceOnline>().create(query));
    }

    @Override
    public boolean isave(ScreenDeviceOnline param) {
        ScreenDeviceOnline select = this.getOne(new QueryWrapper<ScreenDeviceOnline>()
                .eq("screen_id", param.getScreenId()).eq("device_ip", param.getDeviceIp()));
        if (select != null) {
            select.setDeviceIp(param.getDeviceIp());
            select.setScreenId(param.getScreenId());
            select.setUpdateTime(DateUtil.date());
            return this.updateById(select);
        } else {
            return this.saveOrUpdate(param);
        }
    }

    @Override
    public boolean iupdate(ScreenDeviceOnline param) {
        return this.updateById(param);
    }

    @Override
    public ScreenDeviceOnline igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }

    @Override
    public Dict onlineCount(String screenId) {
        Date time = DateUtil.offsetMinute(DateUtil.date(), -30);
        Integer countHistory = this.count(new QueryWrapper<ScreenDeviceOnline>().lt("update_time", time));
        Integer count = this.count(new QueryWrapper<ScreenDeviceOnline>().eq("screen_id", screenId));
        return Dict.create().set("onlineCount", count).set("offlineCount", countHistory);
    }

    @Override
    public List<ScreenDeviceVo> getScreenDeviceVo(String screenId, boolean tfOnlineDevice) {
        QueryWrapper<ScreenDeviceOnline> qs = new QueryWrapper<ScreenDeviceOnline>();
        if (!tfOnlineDevice) {
            Date time = DateUtil.offsetMinute(DateUtil.date(), -30);
            qs.lt("update_time", time);
        }
        qs.eq("screen_id", screenId);
        qs.orderByDesc("update_time");
        List<ScreenDeviceOnline> list = this.list(qs);
        List<ScreenDeviceVo> returnList = new ArrayList<>();
        for (ScreenDeviceOnline vo : list) {
            ScreenDeviceVo screenDeviceVo = new ScreenDeviceVo();
            screenDeviceVo.setDeviceIp(vo.getDeviceIp());
            screenDeviceVo.setRemake(vo.getRemake());
            screenDeviceVo.setUpdateTime(vo.getUpdateTime());
            long msTime=DateUtil.betweenMs(vo.getUpdateTime(), DateUtil.date());
            screenDeviceVo.setOfflineDuration(msTime);
            if(msTime<=600000){
                screenDeviceVo.setTfOnline(true);
            }
            returnList.add(screenDeviceVo);
        }
        return returnList;
    }
}
