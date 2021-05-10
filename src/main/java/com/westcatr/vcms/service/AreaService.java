package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.AreaQuery;
import com.westcatr.vcms.entity.Area;
import com.westcatr.vcms.param.AreaVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 区域表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
public interface AreaService extends IService<Area> {

    @Cacheable(cacheNames = "cache-Area-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<AreaVo> ipage(AreaQuery query);

    @CacheEvict(cacheNames = "cache-Area-page", allEntries = true)
    boolean isave(Area param);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-Area-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-Area-byId", key = "#param.id") })
    boolean iupdate(Area param);

    @Cacheable(cacheNames = "cache-Area-byId", unless = "#result==null")
    AreaVo igetById(String id);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-Area-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-Area-byId", key = "#id") })
    boolean iremove(String id);

    List<AreaVo> treeInfo();
}
