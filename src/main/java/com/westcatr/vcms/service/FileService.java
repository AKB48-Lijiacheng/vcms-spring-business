package com.westcatr.vcms.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.FileQuery;
import com.westcatr.vcms.entity.File;
import com.westcatr.vcms.param.FilePreviewVo;
import com.westcatr.vcms.param.UrlAddParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 上传资源表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
public interface FileService extends IService<File> {

    @Cacheable(cacheNames = "cache-File-page", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    IPage<File> ipage(FileQuery query);

    @CacheEvict(cacheNames = "cache-File-page", allEntries = true)
    File isave(String fileOldPath, String fileName, String remake,MultipartFile multipartFile) throws Exception;

      @Caching(evict = { @CacheEvict(cacheNames = "cache-File-page", allEntries = true),
     @CacheEvict(cacheNames = "cache-File-byId", key = "#id") })
    File iupdate(MultipartFile multipartFile, String fileName, String remake, String id) throws Exception;

    @Cacheable(cacheNames = "cache-File-byId", unless = "#result==null")
    FilePreviewVo igetById(String id);

    @Caching(evict = { @CacheEvict(cacheNames = "cache-File-page", allEntries = true),
            @CacheEvict(cacheNames = "cache-File-byId", key = "#id") })
    boolean iremove(String id);

    List<String> fileExtendPath(String fileType, String filePath);


   @Caching(evict = {
           @CacheEvict(cacheNames = "cache-File-page", allEntries = true),
           @CacheEvict(cacheNames = "cache-File-byId", key = "#urlAddParam.id")
   })
    boolean addUrl(UrlAddParam urlAddParam);

   @Caching(evict = {
           @CacheEvict(cacheNames = "cache-File-page", allEntries = true),
           @CacheEvict(cacheNames = "cache-File-byId", key = "#id")
   })
    void removeGroup(String id);

   @Caching(evict = {
           @CacheEvict(cacheNames = "cache-File-page", allEntries = true),
           @CacheEvict(cacheNames = "cache-File-byId", key = "#id")
   })
    void addGroup(String id,String fileId,Integer intervalTime);

   @Caching(evict = {
           @CacheEvict(cacheNames = "cache-File-page", allEntries = true),
           @CacheEvict(cacheNames = "cache-File-byId", key = "#urlAddParam.id")
   })
   boolean updateUrl(UrlAddParam urlAddParam);

   List<File> listFileByGroupId(String id);
}
