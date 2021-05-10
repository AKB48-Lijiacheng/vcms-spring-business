package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenInfoGroupQuery;
import com.westcatr.vcms.entity.ScreenInfoGroup;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2020-06-19
 */
public interface ScreenInfoGroupService extends IService<ScreenInfoGroup> {
    
    @Cacheable(cacheNames = "cache-ScreenInfoGroup-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<ScreenInfoGroup> ipage(ScreenInfoGroupQuery query);

    @CacheEvict(cacheNames = "cache-ScreenInfoGroup-page", allEntries = true)
    boolean isave(ScreenInfoGroup param);

    @Caching(evict = {
            @CacheEvict(cacheNames = "cache-ScreenInfoGroup-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ScreenInfoGroup-byId", key = "#param.id" )
    })
    boolean iupdate(ScreenInfoGroup param);

    @Cacheable(cacheNames = "cache-ScreenInfoGroup-byId", unless = "#result==null")
    ScreenInfoGroup igetById(String id);

    @Caching(evict = {
        @CacheEvict(cacheNames = "cache-ScreenInfoGroup-page", allEntries = true),
        @CacheEvict(cacheNames = "cache-ScreenInfoGroup-byId", key = "#id" )
    })
    boolean iremove(String id);
}
