package com.westcatr.vcms.param;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileGroupAddParamChild {

    @ApiModelProperty(value = "文件id，多类型文件组用")
    private String fileId;

    @ApiModelProperty(value = "文件间隔时间,多类型文件组用")
    private Integer intervalTime;

    @ApiModelProperty(value = "文件类型，多类型文件组用")
    private String fileType;

    @ApiModelProperty(value = "富文本类型，单独处理，多类型文件组用")
    private String richTexts;

    @ApiModelProperty(value = "更新时间,主要用于返回时的排序")
    private Date updateTime;

    @ApiModelProperty(value = "该字段主要用于返回类型,如果当前type为PPT或者PDF的时候,他分解的图片组会放在这里统一返回")
    private List<String> attributeInfo;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "更新时间,主要用于返回时的排序")
    private String filePath=null;

    @ApiModelProperty(value = "更新时间,主要用于返回时的排序")
    private String fileName=null;

    @ApiModelProperty(value = "自定义字段")
    private String action;
}
