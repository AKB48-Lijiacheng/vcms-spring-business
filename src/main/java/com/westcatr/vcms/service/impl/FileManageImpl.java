package com.westcatr.vcms.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import com.westcatr.rd.base.bweb.exception.MyRuntimeException;
import com.westcatr.vcms.param.MultipartFileParam;
import com.westcatr.vcms.service.IFileManage;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;

@Service()
public class FileManageImpl implements IFileManage {

    @Value("${vcms.upload-path}")
    private String basePath;

    @Override
    public String chunkUploadByMappedByteBuffer(MultipartFileParam param) throws IOException {
        if (param.getTaskId() == null || "".equals(param.getTaskId())) {
            param.setTaskId(UUID.randomUUID().toString());
        }
        /**
         * basePath是我的路径，可以替换为你的 1：原文件名改为UUID 2：创建临时文件，和源文件一个路径 3：如果文件路径不存在重新创建
         */
        String fileName = param.getFile().getOriginalFilename();
        String tempFileName = param.getTaskId() + fileName.substring(fileName.lastIndexOf(".")) + "_tmp";
        String filePath = basePath;
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File tempFile = new File(filePath, tempFileName);
        // 第一步
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        // 第二步
        FileChannel fileChannel = raf.getChannel();
        // 第三步
        long offset = param.getChunk() * param.getSize();
        // 第四步
        byte[] fileData = param.getFile().getBytes();
        // 第五步
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
        // 第六步
        mappedByteBuffer.put(fileData);
        // 第七步
        com.westcatr.vcms.util.FileUtil.freedMappedByteBuffer(mappedByteBuffer);
        fileChannel.close();
        raf.close();
        // 第八步
        boolean isComplete = checkUploadStatus(param, fileName, filePath);
        if (isComplete) {
            renameFile(tempFile, fileName);
        }
        return "";
    }

    @Override
    public boolean renameFile(File toBeRenamed, String toFileNewName) {
        // 检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            return false;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        // 修改文件名
        return toBeRenamed.renameTo(newFile);
    }

    @Override
    public boolean checkUploadStatus(MultipartFileParam param, String fileName, String filePath) throws IOException {
        File confFile = new File(filePath, fileName + ".conf");
        RandomAccessFile confAccessFile = new RandomAccessFile(confFile, "rw");
        // 设置文件长度
        confAccessFile.setLength(param.getChunkTotal());
        // 设置起始偏移量
        confAccessFile.seek(param.getChunk());
        // 将指定的一个字节写入文件中 127，
        confAccessFile.write(Byte.MAX_VALUE);
        byte[] completeStatusList = FileUtil.readBytes(confFile);
        ;
        byte isComplete = Byte.MAX_VALUE;
        for (int i = 0; i < completeStatusList.length && isComplete == Byte.MAX_VALUE; i++) {
            isComplete = (byte) (isComplete & completeStatusList[i]);
            System.out.println("check part " + i + " complete?:" + completeStatusList[i]);
        }
        if (isComplete == Byte.MAX_VALUE) {
            confFile.delete();
            return true;
        }
        return false;
    }

    @Override
    public String getFilePathByType(Integer objectType) {
        // 不同主体对应的文件夹
        Map<Integer, String> typeMap = new HashMap<>();
        typeMap.put(1, "Article");
        typeMap.put(2, "Question");
        typeMap.put(3, "Answer");
        typeMap.put(4, "Courseware");
        typeMap.put(5, "Lesson");
        String objectPath = typeMap.get(objectType);
        if (objectPath == null || "".equals(objectPath)) {
            throw new MyRuntimeException("主体类型不存在");
        }
        return objectPath;
    }
}