package com.westcatr.vcms.param;

import java.util.List;

import lombok.Data;

@Data
public class ScreenUpdateParam {
    private List<String> ids;

    private String resourceType;

    private String resourceName;

    private String resourceId;
}