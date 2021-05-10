package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ConferenceRoomQuery;
import com.westcatr.vcms.entity.ConferenceRoom;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 会议室信息表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
public interface ConferenceRoomService extends IService<ConferenceRoom> {
    
    @Cacheable(cacheNames = "cache-ConferenceRoom-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<ConferenceRoom> ipage(ConferenceRoomQuery query);

    @CacheEvict(cacheNames = "cache-ConferenceRoom-page", allEntries = true)
    boolean isave(ConferenceRoom param);

    @Caching(evict = {
            @CacheEvict(cacheNames = "cache-ConferenceRoom-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ConferenceRoom-byId", key = "#param.id" )
    })
    boolean iupdate(ConferenceRoom param);

    @Cacheable(cacheNames = "cache-ConferenceRoom-byId", unless = "#result==null")
    ConferenceRoom igetById(String id);

    @Caching(evict = {
        @CacheEvict(cacheNames = "cache-ConferenceRoom-page", allEntries = true),
        @CacheEvict(cacheNames = "cache-ConferenceRoom-byId", key = "#id" )
    })
    boolean iremove(String id);
}
