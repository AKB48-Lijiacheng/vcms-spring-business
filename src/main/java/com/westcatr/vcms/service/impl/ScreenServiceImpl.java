package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.lang.Dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenQuery;
import com.westcatr.vcms.entity.Screen;
import com.westcatr.vcms.entity.ScreenDic;
import com.westcatr.vcms.mapper.ScreenMapper;
import com.westcatr.vcms.service.ScreenDeviceOnlineService;
import com.westcatr.vcms.service.ScreenDicService;
import com.westcatr.vcms.service.ScreenService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import java.util.List;

/**
 * <p>
 * 屏幕管理 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
@Service
public class ScreenServiceImpl extends ServiceImpl<ScreenMapper, Screen> implements ScreenService {

    @Autowired
    private ScreenDicService screenDicService;

    @Autowired
    private ScreenDeviceOnlineService screenDeviceOnlineService;

    @Override
    public IPage<Screen> ipage(ScreenQuery query) {
        Page<Screen> screenPages = this.page(query.page(), new WrapperFactory<Screen>().create(query));
        List<Screen> list = screenPages.getRecords();
        list.forEach(x -> {
            x.setScreenDics(screenDicService.list(new QueryWrapper<ScreenDic>().eq("screen_id", x.getId())));
            Dict dict = screenDeviceOnlineService.onlineCount(x.getId());
            x.setOnlineCount(dict.getInt("onlineCount"));
            x.setOfflineCount(dict.getInt("offlineCount"));
        });
        screenPages.setRecords(list);
        return screenPages;
    }

    @Override
    public boolean isave(Screen param) {
        if (this.save(param)) {
            if (param.getScreenDics() != null && param.getScreenDics().size() != 0) {
                List<ScreenDic> screenDics = param.getScreenDics();
                screenDics.forEach(x -> x.setScreenId(param.getId()));
                screenDicService.isaveOrUpdate(screenDics);
            }
        }
        return true;
    }

    @Override
    public boolean iupdate(Screen param) {
        if (this.updateById(param)) {
            if (param.getScreenDics() != null && param.getScreenDics().size() != 0) {
                screenDicService.remove(new QueryWrapper<ScreenDic>().eq("screen_id", param.getId()));
                List<ScreenDic> screenDics = param.getScreenDics();
                screenDics.forEach(x -> {
                    x.setScreenId(param.getId());
                    x.setId(null);
                });
                screenDicService.saveBatch(screenDics);
            }
        }
        return true;
    }

    @Override
    public Screen igetById(String id) {
        Screen screen = this.getById(id);
        screen.setScreenDics(screenDicService.list(new QueryWrapper<ScreenDic>().eq("screen_id", id)));
        return screen;
    }

    @Override
    public boolean iremove(String id) {
        if (this.removeById(id)) {
            screenDicService.remove(new QueryWrapper<ScreenDic>().eq("screen_id", id));
        }
        return true;
    }
}
