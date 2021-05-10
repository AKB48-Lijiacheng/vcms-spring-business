package com.westcatr.vcms.mapper;

import com.westcatr.vcms.entity.ConferenceRoomReservation;
import com.westcatr.vcms.param.ConferenceRoomDateCount;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会议室预定表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
public interface ConferenceRoomReservationMapper extends BaseMapper<ConferenceRoomReservation> {
    List<ConferenceRoomDateCount> schedulCount();
}
