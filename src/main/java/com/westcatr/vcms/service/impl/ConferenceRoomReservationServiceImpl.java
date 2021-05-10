package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.date.DateUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ConferenceRoomReservationQuery;
import com.westcatr.vcms.entity.ConferenceRoomReservation;
import com.westcatr.vcms.mapper.ConferenceRoomReservationMapper;
import com.westcatr.vcms.param.ConferenceRoomDateCount;
import com.westcatr.vcms.service.ConferenceRoomReservationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.westcatr.rd.base.acommon.util.IUserUtil;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import com.westcatr.rd.base.bweb.exception.MyRuntimeException;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会议室预定表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Service
public class ConferenceRoomReservationServiceImpl
        extends ServiceImpl<ConferenceRoomReservationMapper, ConferenceRoomReservation>
        implements ConferenceRoomReservationService {

    @Autowired
    private IUserUtil iUserUtil;

    @Autowired
    private ConferenceRoomReservationService conferenceRoomReservationService;

    @Override
    public IPage<ConferenceRoomReservation> ipage(ConferenceRoomReservationQuery query) {
        QueryWrapper<ConferenceRoomReservation> queryWrapper = new WrapperFactory<ConferenceRoomReservation>()
                .create(query);
        if (query.getStartDateTime() != null) {
            queryWrapper.ge("start_date_time", query.getStartDateTime());
        }
        if (query.getEndDateTime() != null) {
            queryWrapper.le("end_date_time", query.getEndDateTime());
        }
        return this.page(query.page(), queryWrapper);
    }

    @Override
    public boolean isave(ConferenceRoomReservation param) {
        int startCount = this
                .count(new QueryWrapper<ConferenceRoomReservation>().le("start_date_time", param.getStartDateTime())
                        .ge("end_date_time", param.getStartDateTime()).eq("room_id", param.getRoomId()));
        int endCount = this
                .count(new QueryWrapper<ConferenceRoomReservation>().le("start_date_time", param.getEndDateTime())
                        .ge("end_date_time", param.getEndDateTime()).eq("room_id", param.getRoomId()));
        if (startCount != 0 || endCount != 0) {
            throw new MyRuntimeException("该时间段内，所选会议室已被预定，请重新选择");
        }
        param.setReservedName(iUserUtil.getIUser().getFullName());
        param.setRoomName(conferenceRoomReservationService.getById(param.getRoomId()).getRoomName());
        return this.save(param);
    }

    @Override
    public boolean iupdate(ConferenceRoomReservation param) {
        param.setRoomName(conferenceRoomReservationService.getById(param.getRoomId()).getRoomName());
        return this.updateById(param);
    }

    @Override
    public ConferenceRoomReservation igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }

    @Override
    public List<ConferenceRoomDateCount> schedulCount() {
        List<ConferenceRoomDateCount> list = this.baseMapper.schedulCount();
        return list;
    }
}
