package com.westcatr.vcms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "vcms")
public class VcmsProperties {
    /**
     * 上传目录
     */
    private String uploadPath;
}