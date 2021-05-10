package com.westcatr.vcms.component.schedulingTask;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author : lijiacheng
 * @description :FileSliceDelTask
 * ---------------------------------
 * @since : Create in 2020/11/26 13:59
 */
@EnableScheduling
@Component
@Slf4j
public class DayTask {
    @Value("${vcms.upload-path.slice}")
    private String fileSlicePath;

    //定时删除不用的文件分片定时任务
    @Scheduled(fixedRate = 1000 * 60*60*24)//每天执行一次
    public void FileSliceDel() {
     log.info("开始删除无效的分片文件："+fileSlicePath);
        File sliceDir = FileUtil.file(fileSlicePath);
        if (FileUtil.isDirEmpty(sliceDir)) {
            log.info("分片目录为空，不需要删除分片");
            return;
        }
        List<File> fileSort = com.westcatr.vcms.util.FileUtil.getFileSort(fileSlicePath);//按时间升序

        fileSort.forEach(file -> {
            if (System.currentTimeMillis() - file.lastModified() >86400000 ) {//清理超过一天的分片
                FileUtil.del(file);
                if (file.isDirectory()){
                    file.delete();
                }
                log.info("删除分片目录:"+file.getName());
            } else {
                return;//因为是已经升序了，所以时间不是在一天内，那么后面的都是一天外。
            }
        });
        log.info("删除无效分片文件完成："+fileSlicePath);
    }


}



