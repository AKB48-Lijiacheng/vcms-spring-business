package com.westcatr.vcms.mapper;

import com.westcatr.vcms.entity.FileGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 播放集表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
public interface FileGroupMapper extends BaseMapper<FileGroup> {

    @Select("select interval_time from vc_file_file_group where file_id=#{fileId} and file_group_id=#{fileGroupId} ")
    Integer findIntvalTime(String fileId, String fileGroupId);
}
