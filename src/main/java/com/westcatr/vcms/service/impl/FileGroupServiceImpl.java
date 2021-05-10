package com.westcatr.vcms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.westcatr.rd.base.acommon.util.IUserUtil;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import com.westcatr.rd.base.bweb.exception.MyRuntimeException;
import com.westcatr.vcms.component.aop.WebSocketMsgAop;
import com.westcatr.vcms.config.VcmsProperties;
import com.westcatr.vcms.component.webSocket.WebSocketServer;
import com.westcatr.vcms.controller.query.FileGroupQuery;
import com.westcatr.vcms.entity.*;
import com.westcatr.vcms.mapper.FileGroupMapper;
import com.westcatr.vcms.mapper.ScreenInfoMapper;
import com.westcatr.vcms.param.FileGroupAddParam;
import com.westcatr.vcms.param.FileGroupAddParamChild;
import com.westcatr.vcms.param.FileGroupPreviewVo;
import com.westcatr.vcms.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 播放集表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Service
@Slf4j
public class FileGroupServiceImpl extends ServiceImpl<FileGroupMapper, FileGroup> implements FileGroupService {
    @Autowired
    private FileGroupMapper fileGroupMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileGroupService fileGroupService;

    @Autowired
    private FileTextService fileTextService;

    @Autowired
    private VcmsProperties vcmsProperties;

    @Autowired
    private ScreenInfoMapper screenInfoMapper;

    @Autowired
    private ScreenInfoService screenInfoService;

    @Autowired
    private IUserUtil iUserUtil;

    @Value("${vcms.control.path}")
    private String vcmsControlHttpPath;

    @Autowired
   WebSocketMsgAop socketMsg;

    private static final String GROUP_ID = "group_id";

    @Override
    public IPage<FileGroup> ipage(FileGroupQuery query) {
        return this.page(query.page(), new WrapperFactory<FileGroup>().create(query));
    }

   /* @Override
    public boolean isave(FileGroupAddParam param) {
        param.setUserName(iUserUtil.getIUser().getFullName());
        // param.setUserName(iUserUtil.getIUser().getFullName());
        List<FileGroupAddParamChild> list = param.getListFileGroupAddParamChild();
        if (this.saveOrUpdate(param)) {
            if (list == null || list.size() == 0) {
                String id = param.getId();
                // 插入富本文
                if (param.getRichTexts() != null && param.getRichTexts().length != 0) {
                    int index = 0;
                    for (String info : param.getRichTexts()) {
                        FileText fileText = new FileText();
                        fileText.setContentInfo(info);
                        fileText.setGroupId(id);
                        fileText.setSort(index);
                        fileText.setUserName(iUserUtil.getIUser().getFullName());
                        fileTextService.save(fileText);
                        index++;
                    }
                } else {// 更新文件表
                    for (String subtableId : param.getIds()) {
                        File file = fileService.getById(subtableId);
                        if (file != null) {
                            file.setGroupId(id);
                            fileService.updateById(file);
                        }
                    }
                }
            } else {
                int index = 0;
                for (FileGroupAddParamChild fileGroupAddParamChild : list) {
                    if (StrUtil.isAllEmpty(fileGroupAddParamChild.getRichTexts())) {
                        File file = fileService.getById(fileGroupAddParamChild.getFileId());
                        if (file != null) {
                            file.setExtendInfo(fileGroupAddParamChild.getAction());
                            file.setGroupId(param.getId());
                            fileService.updateById(file);
                        }
                    } else {
                        String info = fileGroupAddParamChild.getRichTexts();
                        FileText fileText = new FileText();
                        fileText.setContentInfo(info);
                        fileText.setExtendInfo(fileGroupAddParamChild.getAction());
                        fileText.setGroupId(param.getId());
                        fileText.setSort(index);
                        fileText.setUserName(iUserUtil.getIUser().getFullName());
                        fileTextService.save(fileText);
                        index++;
                    }
                }
            }
        } else {
            throw new MyRuntimeException("文件保存失败");
        }
        return true;
    }

    @Override
    public boolean iupdate(FileGroupAddParam param) {
        String[] ids = param.getIds();
        if (param.getFileType().equals("多类型文件组")) {
            List<FileGroupAddParamChild> list = param.getListFileGroupAddParamChild();
            List<String> listStr = list.stream().map(FileGroupAddParamChild::getFileId).collect(Collectors.toList());
            ids = listStr.toArray(new String[listStr.size()]);
        }
*//*
       if (param.getRichTexts() != null && param.getRichTexts().length != 0) {
            fileTextService.removeByMap(Dict.create().set(GROUP_ID, param.getId()));*//*
        // 先删除再插入
        List<FileText> fileTexts = fileTextService.listByMap(Dict.create().set(GROUP_ID, param.getId()));
        if ( fileTexts.size()>0) {
            fileTextService.removeByMap(Dict.create().set(GROUP_ID, param.getId()));
        } else {
            List<File> oldFileIds = fileService.listByMap(Dict.create().set(GROUP_ID, param.getId()));
            for (File fileId : oldFileIds) {
                if (!Arrays.asList(ids).contains(fileId.getId())) {
                    fileId.setGroupId(null);
                    fileService.updateById(fileId);
                }
            }
        }
        return isave(param);
    }*/


    @Override
    public Boolean iupdate(FileGroupAddParam param) {
        // 先删除再插入
        List<FileText> fileTexts = fileTextService.listByMap(Dict.create().set(GROUP_ID, param.getId()));
        if ( fileTexts.size()>0) {
            fileTextService.removeByMap(Dict.create().set(GROUP_ID, param.getId()));
        }
        //查询所有该组的文件,删除group关联
        String groupId = param.getId();
        List<File> oldFileIds = fileService.listFileByGroupId(groupId);
        if (CollectionUtils.isNotEmpty(oldFileIds)) {
           oldFileIds.forEach(file-> fileService.removeGroup(groupId));
          }
        return isave(param);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean isave(FileGroupAddParam param) {
        param.setUserName(iUserUtil.getIUser().getFullName());
        //如果存在就更新，不在就插入
         this.saveOrUpdate(param);
        List<FileGroupAddParamChild> list = param.getListFileGroupAddParamChild();
        if (CollectionUtils.isNotEmpty(list)){
        //对子文件进行处理
            int index = 0;
            for (FileGroupAddParamChild childFile : list) {
                //如果是富文本类型的文件
                if (StringUtils.isNotBlank(childFile.getRichTexts())) {
                        FileText fileText = new FileText();
                        fileText.setContentInfo(childFile.getRichTexts());
                        fileText.setGroupId(param.getId());
                        fileText.setSort(index);
                        fileText.setUserName(iUserUtil.getIUser().getFullName());
                        fileText.setExtendInfo(childFile.getAction());
                        fileTextService.save(fileText);
                        index++;
                }else {//否则统一处理其他类型的文件
                    if(StringUtils.isNotBlank(childFile.getFileId())) {
                        File file = fileService.getById(childFile.getFileId());
                        if (file != null) {
                            file.setExtendInfo(childFile.getAction());
                            fileService.updateById(file);
                        }
                        //如果是PPT或者PDF类型的，就去File和FileGroup的中间表拿切换时间
                        Integer intervalTime=null;
                        String fileType = file.getFileType();
                        if (null!=fileType &&(file.getFileType().equals("PPT") || file.getFileType().equals("PDF"))){
                         intervalTime = childFile.getIntervalTime();
                    }
                        //添加到中间表
                        fileService.addGroup(param.getId(), childFile.getFileId(),intervalTime);
                    }
                }
            }
        }
        return true;
    }


    @Override
    public FileGroup igetById(String id) {
        FileGroup fileGroup = this.getById(id);
        if (fileGroup == null) {
            throw new MyRuntimeException("资源不存在");
        }
        if (!fileGroup.getFileType().equals("图文") && !fileGroup.getFileType().equals("多类型文件组")) {
            List<FileGroupPreviewVo> list = new ArrayList<>();
            List<File> lFiles = fileService.listFileByGroupId(fileGroup.getId());
       //     List<File> lFiles = fileService.listByMap(Dict.create().set(GROUP_ID, fileGroup.getId()));
            for (File file : lFiles) {
                FileGroupPreviewVo fileGroupPreviewVo = new FileGroupPreviewVo();
                fileGroupPreviewVo.setFileId(file.getId());
                fileGroupPreviewVo.setFileName(file.getFileName());
                fileGroupPreviewVo.setFilePath(file.getFilePath());
                fileGroupPreviewVo.setFileSize(file.getFileSize());
                fileGroupPreviewVo.setUpdateTime(file.getUpdateTime());
                if (file.getFileType().equals("PPT") || file.getFileType().equals("PDF")) {
                    fileGroupPreviewVo
                            .setAttributeInfo(fileService.fileExtendPath(file.getFileType(), file.getFilePath()));
                }
                list.add(fileGroupPreviewVo);
                Collections.sort(list, new Comparator<FileGroupPreviewVo>() {
                    @Override
                    public int compare(FileGroupPreviewVo h1, FileGroupPreviewVo h2) {
                        return h1.getUpdateTime().compareTo(h1.getUpdateTime());
                    }
                });
            }
            fileGroup.setListFileGroupPreviewVo(list);
        } else if (fileGroup.getFileType().equals("多类型文件组")) {
           // List<File> lFiles = fileService.listByMap(Dict.create().set(GROUP_ID, fileGroup.getId()));
            List<File> lFiles = fileService.listFileByGroupId(fileGroup.getId());
            List<FileGroupAddParamChild> returnList = new ArrayList<>();
            List<FileText> lFilesText = fileTextService.listByMap(Dict.create().set(GROUP_ID, fileGroup.getId()));
            for (File file : lFiles) {
                    FileGroupAddParamChild fileGroupAddParamChild = new FileGroupAddParamChild();
                fileGroupAddParamChild.setFileId(file.getId());
                fileGroupAddParamChild.setFileType(file.getFileType());
                fileGroupAddParamChild.setFileName(file.getFileName());
                fileGroupAddParamChild.setAction(file.getExtendInfo());
                fileGroupAddParamChild.setFilePath(file.getFilePath());
                fileGroupAddParamChild.setUpdateTime(file.getUpdateTime());
                // 如果为ppt或PDF
                if (file.getFileType().equals("PPT") || file.getFileType().equals("PDF")) {
                    fileGroupAddParamChild
                            .setAttributeInfo(fileService.fileExtendPath(file.getFileType(), file.getFilePath()));
                      //去中间库拿到ppt切换间隔时间
                    Integer intervalTime=  fileGroupService.findIntvalTime(file.getId(),fileGroup.getId());
                    fileGroupAddParamChild.setIntervalTime(intervalTime);
                }
                returnList.add(fileGroupAddParamChild);
            }
            // 为富文本的时候特殊处理
            for (FileText file : lFilesText) {
                FileGroupAddParamChild fileGroupAddParamChild = new FileGroupAddParamChild();
                fileGroupAddParamChild.setFileId(file.getId());
                fileGroupAddParamChild.setRichTexts(file.getContentInfo());
                fileGroupAddParamChild.setFileType("图文");
                fileGroupAddParamChild.setAction(file.getExtendInfo());
                fileGroupAddParamChild.setUpdateTime(file.getUpdateTime());
                returnList.add(fileGroupAddParamChild);
            }
            if (lFiles != null && lFiles.size() != 0) {
                if (returnList.get(0).getSort() != null) {
                    Collections.sort(returnList, new Comparator<FileGroupAddParamChild>() {
                        @Override
                        public int compare(FileGroupAddParamChild h1, FileGroupAddParamChild h2) {
                            return h1.getSort().compareTo(h2.getSort());
                        }
                    });
                }
            }
            fileGroup.setListFileGroupAddParamChild(returnList);
        } else {
            List<FileGroupPreviewVo> list = new ArrayList<>();
            List<FileText> lFiles = fileTextService.listByMap(Dict.create().set(GROUP_ID, fileGroup.getId()));
            for (FileText file : lFiles) {
                FileGroupPreviewVo fileGroupPreviewVo = new FileGroupPreviewVo();
                fileGroupPreviewVo.setFileId(file.getId());
                fileGroupPreviewVo.setRichText(file.getContentInfo());
                fileGroupPreviewVo.setUpdateTime(file.getUpdateTime());
                list.add(fileGroupPreviewVo);
            }
            if (list.get(0).getSort() != null) {
                Collections.sort(list, new Comparator<FileGroupPreviewVo>() {
                    @Override
                    public int compare(FileGroupPreviewVo h1, FileGroupPreviewVo h2) {
                        return h1.getSort().compareTo(h2.getSort());
                    }
                });
            }
            fileGroup.setListFileGroupPreviewVo(list);
        }
        return fileGroup;
    }

    @Override
    public Integer findIntvalTime(String fileId, String fileGroupId) {
        return  fileGroupMapper.findIntvalTime(fileId,fileGroupId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean iremove(String id) {
        FileGroup fileGroup = this.getById(id);
        if (this.removeById(id)) {
            if (fileGroup.getFileType().equals("图文")) {
                fileTextService.removeByMap(Dict.create().set(GROUP_ID, id));
            } else {
                List<File> updateFile = fileService.listByMap(Dict.create().set(GROUP_ID, id));
                for (File file : updateFile) {
                    fileService.removeGroup(id);
                }
            }
            List<ScreenInfo> screenInfo = screenInfoService.list(new QueryWrapper<ScreenInfo>().eq("resource_id", id));
            if ( CollectionUtil.isNotEmpty(screenInfo) ) {
                screenInfo.forEach(sc->screenInfoMapper.updateResource(sc.getId()));
            }
        }
        return true;
    }


}
