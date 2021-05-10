package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenInfoGroupQuery;
import com.westcatr.vcms.entity.ScreenInfo;
import com.westcatr.vcms.entity.ScreenInfoGroup;
import com.westcatr.vcms.mapper.ScreenInfoGroupMapper;
import com.westcatr.vcms.service.ScreenInfoGroupService;
import com.westcatr.vcms.service.ScreenInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-06-19
 */
@Service
public class ScreenInfoGroupServiceImpl extends ServiceImpl<ScreenInfoGroupMapper, ScreenInfoGroup>
        implements ScreenInfoGroupService {

    @Autowired
    private ScreenInfoService screenInfoService;

    @Override
    public IPage<ScreenInfoGroup> ipage(ScreenInfoGroupQuery query) {
        Page<ScreenInfoGroup> pageInfo = this.page(query.page(), new WrapperFactory<ScreenInfoGroup>().create(query));
        List<ScreenInfoGroup> list = pageInfo.getRecords();
        list.forEach(x -> x
                .setDeviceCount(screenInfoService.count(new QueryWrapper<ScreenInfo>().eq("group_id", x.getId()))));
        pageInfo.setRecords(list);
        return pageInfo;
    }

    @Override
    public boolean isave(ScreenInfoGroup param) {
        return this.save(param);
    }

    @Override
    public boolean iupdate(ScreenInfoGroup param) {
        return this.updateById(param);
    }

    @Override
    public ScreenInfoGroup igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }
}
