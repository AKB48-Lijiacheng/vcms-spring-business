package com.westcatr.vcms.param;

import java.util.List;
import com.westcatr.vcms.entity.Area;
import lombok.Data;

@Data
public class AreaVo extends Area {
    private List<Area> children;
}