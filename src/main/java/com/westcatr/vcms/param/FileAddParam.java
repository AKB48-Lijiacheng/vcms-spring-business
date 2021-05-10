package com.westcatr.vcms.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileAddParam {
    private String filePath;
    private String fileName;
    private String remake;
   private MultipartFile file;
}