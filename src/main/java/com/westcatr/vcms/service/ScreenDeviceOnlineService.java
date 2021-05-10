package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import cn.hutool.core.lang.Dict;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenDeviceOnlineQuery;
import com.westcatr.vcms.entity.ScreenDeviceOnline;
import com.westcatr.vcms.param.ScreenDeviceVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 使用屏幕在线设备表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
public interface ScreenDeviceOnlineService extends IService<ScreenDeviceOnline> {

    @Cacheable(cacheNames = "cache-ScreenDeviceOnline-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<ScreenDeviceOnline> ipage(ScreenDeviceOnlineQuery query);

    @CacheEvict(cacheNames = "cache-ScreenDeviceOnline-page", allEntries = true)
    boolean isave(ScreenDeviceOnline param);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-ScreenDeviceOnline-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ScreenDeviceOnline-byId", key = "#param.id") })
    boolean iupdate(ScreenDeviceOnline param);

    @Cacheable(cacheNames = "cache-ScreenDeviceOnline-byId", unless = "#result==null")
    ScreenDeviceOnline igetById(String id);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-ScreenDeviceOnline-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-ScreenDeviceOnline-byId", key = "#id") })
    boolean iremove(String id);

    Dict onlineCount(String screenId);

    List<ScreenDeviceVo> getScreenDeviceVo(String screenId,boolean tfOnlineDevice);
}
