package com.westcatr.vcms.param;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * FileGroupPreviewVo
 */
@Data
public class FileGroupPreviewVo {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "富文本内容，查询富文本时候返回")
    private String richText;

    @ApiModelProperty(value = "文件id或者富文本id")
    private String fileId;

    @ApiModelProperty(value = "文件的更新时间，便于排序")
    private Date updateTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "如果当前文件是pdf或者图片，后台会返回分解的图片地址放到数组一起返回")
    private List<String> attributeInfo;
    
}