package com.westcatr.vcms.param;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MultipartFileParam {
    @ApiModelProperty("文件传输任务ID")
    private String taskId;

    @ApiModelProperty("当前为第几分片")
    private int chunk;

    @ApiModelProperty("每个分块的大小")
    private long size;


    @ApiModelProperty("分片总数")
    private int chunkTotal;

    @ApiModelProperty("分块文件传输对象")
    private MultipartFile file;
}