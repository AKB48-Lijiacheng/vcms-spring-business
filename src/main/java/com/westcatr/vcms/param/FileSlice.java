package com.westcatr.vcms.param;

import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FileSlice {
    @ApiModelProperty("文件上下文编号")
    private String id;
    @ApiModelProperty("分片序号")
    private Integer seq;
    @ApiModelProperty("分片文件")
    private MultipartFile file;
}