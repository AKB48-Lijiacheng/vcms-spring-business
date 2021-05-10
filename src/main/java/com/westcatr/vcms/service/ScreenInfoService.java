package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenInfoQuery;
import com.westcatr.vcms.entity.ScreenInfo;
import com.westcatr.vcms.param.ScreenUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 屏幕信息表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-06-18
 */
public interface ScreenInfoService extends IService<ScreenInfo> {
    
     @Cacheable(cacheNames = "cache-ScreenInfo-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<ScreenInfo> ipage(ScreenInfoQuery query);

    @CacheEvict(cacheNames = "cache-ScreenInfo-page", allEntries = true)
    boolean isave(ScreenInfo param);

    @CacheEvict(cacheNames = "cache-ScreenInfo-page", allEntries = true)
    String isaveMobile(ScreenInfo param);

    @Caching(evict = {
            @CacheEvict(cacheNames = "cache-ScreenInfo-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ScreenInfo-byId", key = "#param.id" )
    })
    boolean iupdate(ScreenInfo param);

    @Cacheable(cacheNames = "cache-ScreenInfo-byId", unless = "#result==null")
    ScreenInfo igetById(String id);

    @Caching(evict = {
        @CacheEvict(cacheNames = "cache-ScreenInfo-page", allEntries = true),
        @CacheEvict(cacheNames = "cache-ScreenInfo-byId", key = "#id" )
    })
    boolean iremove(String id);

    @CacheEvict(cacheNames = "cache-ScreenInfo-page", allEntries = true)
    ScreenInfo isaveOrUpdate(ScreenInfo param);

    @CacheEvict(cacheNames = "cache-ScreenInfo-page", allEntries = true)
    boolean iBatchUpdate(ScreenUpdateParam param);

    List<ScreenInfo> listByIds(String[] ids);
}
