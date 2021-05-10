package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.FileTextQuery;
import com.westcatr.vcms.entity.FileText;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 富文本存储表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
public interface FileTextService extends IService<FileText> {
    
    @Cacheable(cacheNames = "cache-FileText-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<FileText> ipage(FileTextQuery query);

    @CacheEvict(cacheNames = "cache-FileText-page", allEntries = true)
    boolean isave(FileText param);

    @Caching(evict = {
            @CacheEvict(cacheNames = "cache-FileText-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-FileText-byId", key = "#param.id" )
    })
    boolean iupdate(FileText param);

    @Cacheable(cacheNames = "cache-FileText-byId", unless = "#result==null")
    FileText igetById(String id);

    @Caching(evict = {
        @CacheEvict(cacheNames = "cache-FileText-page", allEntries = true),
        @CacheEvict(cacheNames = "cache-FileText-byId", key = "#id" )
    })
    boolean iremove(String id);
}
