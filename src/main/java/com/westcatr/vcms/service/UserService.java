package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.UserQuery;
import com.westcatr.vcms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
public interface UserService extends IService<User> {
    
    @Cacheable(cacheNames = "cache-User-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<User> ipage(UserQuery query);

    @CacheEvict(cacheNames = "cache-User-page", allEntries = true)
    boolean isave(User param);

    @Caching(evict = {
            @CacheEvict(cacheNames = "cache-User-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-User-byId", key = "#param.id" )
    })
    boolean iupdate(User param);

    @Cacheable(cacheNames = "cache-User-byId", unless = "#result==null")
    User igetById(String id);

    @Caching(evict = {
        @CacheEvict(cacheNames = "cache-User-page", allEntries = true),
        @CacheEvict(cacheNames = "cache-User-byId", key = "#id" )
    })
    boolean iremove(String id);
}
