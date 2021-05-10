package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ConferenceRoomQuery;
import com.westcatr.vcms.entity.ConferenceRoom;
import com.westcatr.vcms.mapper.ConferenceRoomMapper;
import com.westcatr.vcms.service.ConferenceRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import java.util.List;

/**
 * <p>
 * 会议室信息表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Service
public class ConferenceRoomServiceImpl extends ServiceImpl<ConferenceRoomMapper, ConferenceRoom> implements ConferenceRoomService {

    @Override
    public IPage<ConferenceRoom> ipage(ConferenceRoomQuery query) {
        return this.page(query.page(), new WrapperFactory<ConferenceRoom>().create(query));
    }

    @Override
    public boolean isave(ConferenceRoom param) {
        return this.save(param);
    }

    @Override
    public boolean iupdate(ConferenceRoom param) {
        return this.updateById(param);
    }

    @Override
    public ConferenceRoom igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }
}
