package com.westcatr.vcms.param;

import java.util.List;

import com.westcatr.vcms.entity.File;

import lombok.Data;

/**
 * 文件信息预览扩展属性
 */
@Data
public class FilePreviewVo extends File {
    private List<String> attributeInfo;
}