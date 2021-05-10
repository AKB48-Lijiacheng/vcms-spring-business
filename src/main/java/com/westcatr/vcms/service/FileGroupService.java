package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.FileGroupQuery;
import com.westcatr.vcms.entity.FileGroup;
import com.westcatr.vcms.param.FileGroupAddParam;
import com.westcatr.vcms.param.FileGroupPreviewVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 播放集表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
public interface FileGroupService extends IService<FileGroup> {

    @Cacheable(cacheNames = "cache-FileGroup-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<FileGroup> ipage(FileGroupQuery query);

    @CacheEvict(cacheNames = "cache-FileGroup-page", allEntries = true)
    Boolean isave(FileGroupAddParam param);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-FileGroup-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-FileGroup-byId", key = "#param.id") })
    Boolean iupdate(FileGroupAddParam param);

    @Cacheable(cacheNames = "cache-FileGroup-byId", unless = "#result==null")
    FileGroup igetById(String id);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-FileGroup-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-FileGroup-byId",condition="#id==null")})
    Boolean iremove(String id);

    Integer findIntvalTime(String fileId, String fileGroupId);
}
