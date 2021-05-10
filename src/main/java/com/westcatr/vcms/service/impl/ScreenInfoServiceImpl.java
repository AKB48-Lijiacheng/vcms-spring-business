package com.westcatr.vcms.service.impl;

import com.westcatr.vcms.component.aop.WebSocketMsgAop;
import com.westcatr.vcms.entity.File;
import com.westcatr.vcms.param.FilePreviewVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.ScreenInfoQuery;
import com.westcatr.vcms.entity.ScreenInfo;
import com.westcatr.vcms.mapper.ScreenInfoMapper;
import com.westcatr.vcms.param.FileGroupAddParamChild;
import com.westcatr.vcms.param.ScreenUpdateParam;
import com.westcatr.vcms.service.FileGroupService;
import com.westcatr.vcms.service.FileService;
import com.westcatr.vcms.service.ScreenInfoService;
import com.westcatr.vcms.component.webSocket.WebSocketServer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.westcatr.rd.base.acommon.vo.IResult;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import com.westcatr.rd.base.bweb.exception.MyRuntimeException;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 屏幕信息表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-06-18
 */
@Slf4j
@Service
public class ScreenInfoServiceImpl extends ServiceImpl<ScreenInfoMapper, ScreenInfo> implements ScreenInfoService {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileGroupService fileGroupService;

   @Autowired
   WebSocketMsgAop socketMsg;

    @Override
    public IPage<ScreenInfo> ipage(ScreenInfoQuery query) {
        Page<ScreenInfo> pages = this.page(query.page(), new WrapperFactory<ScreenInfo>().create(query));
        List<ScreenInfo> lists = pages.getRecords();
        lists.forEach(x -> {
            if (x.getResourceId() != null) {
                if (x.getResourceType().equals("源文件")||x.getResourceType().equals("监控")) {
                    try {
                        FilePreviewVo file = fileService.igetById(x.getResourceId());
                        x.setFile(file);
                    } catch (Exception e) {
                        x.setFile(null);
                    }
                } else {
                    try {
                        x.setFileGroup(fileGroupService.igetById(x.getResourceId()));
                    } catch (Exception e) {
                        x.setFileGroup(null);
                    }
                }
            }
            long msTime = DateUtil.betweenMs(x.getUpdateTime(), DateUtil.date());
            x.setOfflineDuration(msTime);
            if (msTime <= 600000) {
                x.setTfOnline(1);
            } else {
                x.setTfOnline(0);
            }
        });
        pages.setRecords(lists);
        return pages;
    }

    @Override
    public boolean isave(ScreenInfo param) {
        int count = this.count(new QueryWrapper<ScreenInfo>().eq("device_id", param.getDeviceId()));
        if (count != 0|| StringUtils.isBlank(param.getDeviceId())) {
            throw new MyRuntimeException("该设备已存在，无法添加!");
        } else {
            this.save(param);
        }
        return true;
    }

    @Override
    public String isaveMobile(ScreenInfo param) {
        int count = this.count(new QueryWrapper<ScreenInfo>().eq("device_id", param.getDeviceId()));
        if (count != 0) {
            return "该设备已经存在，请继续操作";
        } else {
            this.save(param);
        }
        return "成功";
    }

    @Override
    public boolean iupdate(ScreenInfo param) {
        if (this.updateById(param)) {
            if (param.getResourceId() == null) {
                this.baseMapper.updateResource(param.getId());
            }
            ScreenInfo screenInfo = this.getById(param.getId());
            BeanUtil.copyProperties(param, screenInfo);
            screenInfo = this.isaveOrUpdate(screenInfo);
            //如果是播放库
            if(screenInfo.getResourceType().equals("播放库")&&screenInfo.getFileGroup()!=null){
                if(screenInfo.getFileGroup().getFileType().equals("多类型文件组")&&screenInfo.getFileGroup()!=null){
                    String actionJson=screenInfo.getAction();
                    Integer indexJson= JSON.parseObject(screenInfo.getAction()).getInteger("multiple");
                   List<FileGroupAddParamChild> fileGroupAddParamChildList = screenInfo.getFileGroup().getListFileGroupAddParamChild();
                   if (null!=indexJson&& !fileGroupAddParamChildList.isEmpty()){
                    FileGroupAddParamChild fileGroupAddParamChild=fileGroupAddParamChildList.get(indexJson);
                    //如果是PDF或PPT的文件 那么要把切换时间intervalTime 拼接到action中
                        Integer intervalTime = fileGroupAddParamChild.getIntervalTime();
                        List<String> types = Arrays.asList("PDF", "PPT");
                        if (intervalTime!=null&&types.contains(fileGroupAddParamChild.getFileType())){//如果是PDF和PPT类型
                            JSONObject actionJ = JSON.parseObject(actionJson);
                           actionJ.put("intervalTime",intervalTime);
                           actionJson=actionJ.toJSONString();
                        }
                        fileGroupAddParamChild.setAction(actionJson);
                    fileGroupAddParamChildList.set(indexJson, fileGroupAddParamChild);
                    screenInfo.getFileGroup().setListFileGroupAddParamChild(fileGroupAddParamChildList);
                    }
                }
            }else if (screenInfo.getResourceType().equals("源文件")&&screenInfo.getResourceId()!=null){  //如果是源文件
                //如果是单文件ppt,pdf就把intervalTime拼接到action中，同时保存到数据库
                File resourceFile = fileService.getById(screenInfo.getResourceId());
                String fileType = resourceFile.getFileType();  //获取文件类型
                if ("PDF".equalsIgnoreCase(fileType)||"PPT".equalsIgnoreCase(fileType)) {
                    resourceFile.setIntervalTime(param.getIntervalTime());
                    fileService.updateById(resourceFile);//更新间隔时间
                    String actionJson=screenInfo.getAction();
                    JSONObject actionJ = JSON.parseObject(actionJson);
                    actionJ.put("intervalTime",param.getIntervalTime());
                 screenInfo.setAction(actionJ.toJSONString());//拼接成功
                }
            }
            String deviceId = screenInfo.getDeviceId();
            if (null==screenInfo){
                throw  new MyRuntimeException("设备不在线哦");
            }
            try {
                WebSocketServer.webSocketSet.get(deviceId + "-view")
                        .sendMessage(JSON.toJSONString(IResult.ok(screenInfo)));
            } catch (Exception e) {
                System.out.println(JSON.toJSONString(screenInfo));
                log.error("设备不在线");
            }
        }
        return true;
    }

    @Override
    public ScreenInfo igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }

    @Override
    public ScreenInfo isaveOrUpdate(ScreenInfo param) {
        if (param.getDeviceId() == null) {
            throw new MyRuntimeException("设备id不存在，请赋予权限后重试!");
        }
        List<ScreenInfo> screenInfoList = this
                .list(new QueryWrapper<ScreenInfo>().eq("device_id", param.getDeviceId()));
        if (screenInfoList.size() > 1) {
            throw new MyRuntimeException("该设备已存在，无法添加!");
        }
        param.setLastOnline(DateUtil.date());
        // 控制端
        if (param.isTfControlEnd()) {
            if (screenInfoList == null || screenInfoList.size() == 0) {
                throw new MyRuntimeException("设备不存在，无法进行控制!");
            } else {
                param.setId(screenInfoList.get(0).getId());
                this.updateById(param);
            }
        } else {
            if (screenInfoList == null || screenInfoList.size() == 0) {
                this.save(param);
            } else {
                param.setId(screenInfoList.get(0).getId());
                this.updateById(param);
            }
        }
        // 查询设备是否在线和离线时长
        ScreenInfo result = this.getOne(new QueryWrapper<ScreenInfo>().eq("device_id", param.getDeviceId()));
        long msTime = DateUtil.betweenMs(result.getLastOnline(), DateUtil.date());
        result.setOfflineDuration(msTime);
        if (msTime <= 600000) {
            result.setTfOnline(1);
        } else {
            result.setTfOnline(0);
        }
        if (result.getResourceId() != null) {
            if (result.getResourceType().equals("源文件")) {
                result.setFile(fileService.igetById(result.getResourceId()));
            }else {
                result.setFileGroup(fileGroupService.igetById(result.getResourceId()));
            }
        }
        return result;
    }

    @Override
    public boolean iBatchUpdate(ScreenUpdateParam param) {
        List<String> ids = param.getIds();
        for (String id : ids) {
            ScreenInfo screenInfo = this.getById(id);
            screenInfo.setResourceId(param.getResourceId());
            screenInfo.setResourceName(param.getResourceName());
            screenInfo.setResourceType(param.getResourceType());
            this.updateById(screenInfo);
        }
        return true;
    }

    @Override
    public List<ScreenInfo> listByIds(String[] ids) {
        QueryWrapper<ScreenInfo> queryWrapper = new QueryWrapper<ScreenInfo>();
        queryWrapper.in("id", ids);
        for(int i=0;i<ids.length;i++){
            ScreenInfo screenInfo =this.getById(ids[i]);
            screenInfo.setSort(i);
            this.updateById(screenInfo);
        }
        List<ScreenInfo> list = this.list(queryWrapper);
        return list;
    }
}