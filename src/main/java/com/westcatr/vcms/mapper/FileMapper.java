package com.westcatr.vcms.mapper;

import com.westcatr.vcms.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 上传资源表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
public interface FileMapper extends BaseMapper<File> {

    List<File> listFileByGroupId(String id);

    void removeGroup(String groupId);

    @Insert("INSERT INTO vc_file_file_group (file_id, file_group_id,interval_time)VALUES(#{fileId},#{groupId},#{intervalTime})")
    void addGroup(@Param("groupId") String groupId,@Param("fileId")  String fileId,@Param("intervalTime") Integer intervalTime);

    void deleteFromMid(String id);
}
