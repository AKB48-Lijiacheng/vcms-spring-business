package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ConferenceRoomReservationQuery;
import com.westcatr.vcms.entity.ConferenceRoomReservation;
import com.westcatr.vcms.param.ConferenceRoomDateCount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会议室预定表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
public interface ConferenceRoomReservationService extends IService<ConferenceRoomReservation> {

    @Cacheable(cacheNames = "cache-ConferenceRoomReservation-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<ConferenceRoomReservation> ipage(ConferenceRoomReservationQuery query);

    @CacheEvict(cacheNames = "cache-ConferenceRoomReservation-page", allEntries = true)
    boolean isave(ConferenceRoomReservation param);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-ConferenceRoomReservation-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ConferenceRoomReservation-byId", key = "#param.id") })
    boolean iupdate(ConferenceRoomReservation param);

    @Cacheable(cacheNames = "cache-ConferenceRoomReservation-byId", unless = "#result==null")
    ConferenceRoomReservation igetById(String id);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-ConferenceRoomReservation-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ConferenceRoomReservation-byId", key = "#id") })

    boolean iremove(String id);

    List<ConferenceRoomDateCount> schedulCount();
}
