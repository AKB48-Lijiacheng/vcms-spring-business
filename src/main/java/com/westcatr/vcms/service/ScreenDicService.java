package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenDicQuery;
import com.westcatr.vcms.entity.ScreenDic;
import com.westcatr.vcms.param.ScreenDeviceVo;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 屏幕分组对应表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-06-11
 */
public interface ScreenDicService extends IService<ScreenDic> {
    
    @Cacheable(cacheNames = "cache-ScreenDic-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<ScreenDic> ipage(ScreenDicQuery query);

    @CacheEvict(cacheNames = "cache-ScreenDic-page", allEntries = true)
    boolean isave(ScreenDic param);

    @Caching(evict = {
            @CacheEvict(cacheNames = "cache-ScreenDic-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ScreenDic-byId", key = "#param.id" )
    })
    boolean iupdate(ScreenDic param);

    @Cacheable(cacheNames = "cache-ScreenDic-byId", unless = "#result==null")
    ScreenDic igetById(String id);

    @Caching(evict = {
        @CacheEvict(cacheNames = "cache-ScreenDic-page", allEntries = true),
        @CacheEvict(cacheNames = "cache-ScreenDic-byId", key = "#id" )
    })
    boolean iremove(String id);

    boolean isaveOrUpdate(List<ScreenDic> list);

}
