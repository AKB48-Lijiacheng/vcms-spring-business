package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenQuery;
import com.westcatr.vcms.entity.Screen;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 屏幕管理 服务类
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
public interface ScreenService extends IService<Screen> {
    
    @Cacheable(cacheNames = "cache-Screen-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<Screen> ipage(ScreenQuery query);

    @CacheEvict(cacheNames = "cache-Screen-page", allEntries = true)
    boolean isave(Screen param);

    @Caching(evict = {
            @CacheEvict(cacheNames = "cache-Screen-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-Screen-byId", key = "#param.id" )
    })
    boolean iupdate(Screen param);

    @Cacheable(cacheNames = "cache-Screen-byId", unless = "#result==null")
    Screen igetById(String id);

    @Caching(evict = {
        @CacheEvict(cacheNames = "cache-Screen-page", allEntries = true),
        @CacheEvict(cacheNames = "cache-Screen-byId", key = "#id" )
    })
    boolean iremove(String id);
}
