package com.westcatr.vcms.mapper;

import com.westcatr.vcms.entity.Area;
import com.westcatr.vcms.param.AreaVo;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 区域表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
public interface AreaMapper extends BaseMapper<Area> {
    List<AreaVo> queryForMenu();
}
