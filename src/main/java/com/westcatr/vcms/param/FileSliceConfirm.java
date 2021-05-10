package com.westcatr.vcms.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FileSliceConfirm {
    @ApiModelProperty(value = "文件上下文编号", required = true)
    private String id;
    @ApiModelProperty(value = "文件名称", required = true)
    private String name;
    @ApiModelProperty(value = "总字节数", required = true)
    private Long byteLength;
    @ApiModelProperty(value = "分片个数", required = true)
    private Integer sliceLength;
}