package com.westcatr.vcms.service;

import java.io.File;
import java.io.IOException;

import com.westcatr.vcms.param.MultipartFileParam;

public interface IFileManage {
    String chunkUploadByMappedByteBuffer(MultipartFileParam param) throws IOException;

    boolean renameFile(File toBeRenamed, String toFileNewName);

    boolean checkUploadStatus(MultipartFileParam param,String fileName,String filePath) throws IOException;

    String getFilePathByType(Integer objectType);
}