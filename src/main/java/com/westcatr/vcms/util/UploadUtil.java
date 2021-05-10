package com.westcatr.vcms.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传工具类
 *
 */
@Slf4j
public final class UploadUtil {
    /**
     * 上传文件，默认文件名格式，yyyyMMddHHmmssS
     *
     * @param uploadPath
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public static String upload(String uploadPath, MultipartFile multipartFile) throws Exception {
        return upload(uploadPath, multipartFile, new DefaultUploadFileNameHandleImpl());
    }


    /**
     * 上传文件
     *
     * @param uploadPath           上传目录
     * @param multipartFile        上传文件
     * @param uploadFileNameHandle 回调
     * @return
     * @throws Exception
     */
    public static String upload(String uploadPath, MultipartFile multipartFile,
            UploadFileNameHandle uploadFileNameHandle) throws Exception {
        // 获取输入流
        InputStream inputStream = multipartFile.getInputStream();
        // 文件保存目录
        File saveDir = new File(uploadPath);
        // 判断目录是否存在，不存在，则创建，如创建失败，则抛出异常
        if (!saveDir.exists()) {
            boolean flag = saveDir.mkdirs();
            if (!flag) {
                throw new RuntimeException("创建" + saveDir + "目录失败！");
            }
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String saveFileName;
        if (uploadFileNameHandle == null) {
            saveFileName = new DefaultUploadFileNameHandleImpl().handle(originalFilename);
        } else {
            saveFileName = uploadFileNameHandle.handle(originalFilename);
        }
        log.info("saveFileName = " + saveFileName);

        File saveFile = new File(saveDir, saveFileName);
        // 保存文件到服务器指定路径
        FileUtil.writeFromStream(inputStream, saveFile);
        return saveFileName;
    }

    /**
     * 删除删除的文件
     *
     * @param uploadPath
     * @param saveFileName
     */
    public static void deleteQuietly(String uploadPath, String saveFileName) {
        File saveDir = new File(uploadPath);
        File saveFile = new File(saveDir, saveFileName);
        log.debug("删除文件：" + saveFile);
        FileUtil.del(saveFile);
    }

   public static String uploadMutiFile(String uploadPath, String fileName, MultipartFile multipartFile) throws Exception {
      File saveDir = new File(uploadPath);
      // 判断目录是否存在，不存在，则创建，如创建失败，则抛出异常
      if (!saveDir.exists()) {
         boolean flag = saveDir.mkdirs();
         if (!flag) {
            throw new RuntimeException("创建" + saveDir + "目录失败！");
         }
      }
      File saveFile = new File(saveDir, fileName);
       multipartFile.transferTo(saveFile);
      return saveDir+fileName;
   }

   public static interface UploadFileNameHandle {
        /**
         * 回调处理接口
         *
         * @param originalFilename
         * @return
         */
        String handle(String originalFilename);
    }

    public static class DefaultUploadFileNameHandleImpl implements UploadFileNameHandle {

        @Override
        public String handle(String originalFilename) {
            // 文件后缀
            String fileExtension = FileUtil.extName(originalFilename);
            // 这里可自定义文件名称，比如按照业务类型/文件格式/日期
            // 此处按照文件日期存储
            String dateString = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssS");
            String fileName = dateString + "." + fileExtension;
            return fileName;
        }
    }





}