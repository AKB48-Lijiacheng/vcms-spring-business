package com.westcatr.vcms.param;

import java.util.List;

import com.westcatr.vcms.entity.FileGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileGroupAddParam extends FileGroup {

    @ApiModelProperty(value = "文件id，每次保存的时候都需要把现有关联的文件id传入")
    private String[] ids;

    @ApiModelProperty(value = "富文本内容，无论更新还是编辑，点击保存的时候涉及到富文本都需要传入")
    private String[] richTexts;

    /* @ApiModelProperty(value = "多文件类型组用,所有选择文件传入这里")
    private List<FileGroupAddParamChild> listFileGroupAddParamChild; */
}