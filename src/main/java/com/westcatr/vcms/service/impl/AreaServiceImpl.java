package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.bean.BeanUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.AreaQuery;
import com.westcatr.vcms.entity.Area;
import com.westcatr.vcms.mapper.AreaMapper;
import com.westcatr.vcms.param.AreaVo;
import com.westcatr.vcms.service.AreaService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public IPage<AreaVo> ipage(AreaQuery query) {
        IPage<Area> pages = this.page(query.page(), new WrapperFactory<Area>().create(query).isNull("pid"));
        List<Area> listArea = pages.getRecords();
        List<AreaVo> listAreaVo = new ArrayList<>();
        for (Area area : listArea) {
            AreaVo areaVo = new AreaVo();
            BeanUtil.copyProperties(area, areaVo);
            areaVo.setChildren(this.list(new QueryWrapper<Area>().eq("pid", area.getId()).orderByAsc("sort")));
            listAreaVo.add(areaVo);
        }
        IPage<AreaVo> returnPage = new Page<AreaVo>();
        returnPage.setCurrent(pages.getCurrent());
        returnPage.setPages(pages.getPages());
        returnPage.setRecords(listAreaVo);
        returnPage.setSize(pages.getSize());
        returnPage.setTotal(pages.getTotal());
        return returnPage;
    }

    @Override
    public boolean isave(Area param) {
        return this.save(param);
    }

    @Override
    public boolean iupdate(Area param) {
        return this.updateById(param);
    }

    @Override
    public AreaVo igetById(String id) {
        Area area = this.getById(id);
        AreaVo areaVo = new AreaVo();
        BeanUtil.copyProperties(area, areaVo);
        areaVo.setChildren(this.list(new QueryWrapper<Area>().eq("pid", area.getId()).orderByAsc("sort")));
        return areaVo;
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }

    @Override
    public List<AreaVo> treeInfo() {
        //List<Area> listArea = this.list(new QueryWrapper<Area>().isNull("pid"));
        List<AreaVo> listAreaVo = this.baseMapper.queryForMenu();/*  new ArrayList<>();
        for (Area area : listArea) {
            AreaVo areaVo = new AreaVo();
            BeanUtil.copyProperties(area, areaVo);
            List<Area> child=this.list(new QueryWrapper<Area>().eq("pid", area.getId()).orderByAsc("sort"));
            List<AreaVo> childVo=new ArrayList();
            BeanUtil.copyProperties(child, childVo);
            for (AreaVo vo : childVo) {
                
            }
            areaVo.setChildArea();
            listAreaVo.add(areaVo);
        } */
        return listAreaVo;
    }
}
