package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenDicQuery;
import com.westcatr.vcms.entity.ScreenDic;
import com.westcatr.vcms.mapper.ScreenDicMapper;
import com.westcatr.vcms.service.ScreenDicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import java.util.List;

/**
 * <p>
 * 屏幕分组对应表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-06-11
 */
@Service
public class ScreenDicServiceImpl extends ServiceImpl<ScreenDicMapper, ScreenDic> implements ScreenDicService {

    @Override
    public IPage<ScreenDic> ipage(ScreenDicQuery query) {
        return this.page(query.page(), new WrapperFactory<ScreenDic>().create(query));
    }

    @Override
    public boolean isave(ScreenDic param) {
        return this.save(param);
    }

    @Override
    public boolean iupdate(ScreenDic param) {
        return this.updateById(param);
    }

    @Override
    public ScreenDic igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean isaveOrUpdate(List<ScreenDic> list) {
        return this.saveOrUpdateBatch(list);
    }
}
