package com.westcatr.vcms.mapper;

import com.westcatr.vcms.entity.ScreenInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 屏幕信息表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-06-18
 */
public interface ScreenInfoMapper extends BaseMapper<ScreenInfo> {

    void updateResource(String id);
}
