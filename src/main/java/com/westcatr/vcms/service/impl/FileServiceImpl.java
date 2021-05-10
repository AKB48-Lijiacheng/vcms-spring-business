package com.westcatr.vcms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.westcatr.rd.base.acommon.util.IUserUtil;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import com.westcatr.rd.base.bweb.exception.MyRuntimeException;
import com.westcatr.vcms.component.aop.WebSocketMsgAop;
import com.westcatr.vcms.config.VcmsProperties;
import com.westcatr.vcms.controller.query.FileQuery;
import com.westcatr.vcms.entity.File;
import com.westcatr.vcms.entity.ScreenInfo;
import com.westcatr.vcms.mapper.FileMapper;
import com.westcatr.vcms.mapper.ScreenInfoMapper;
import com.westcatr.vcms.param.FilePreviewVo;
import com.westcatr.vcms.param.UrlAddParam;
import com.westcatr.vcms.service.FileService;
import com.westcatr.vcms.service.ScreenInfoService;
import com.westcatr.vcms.util.FileToFile;
import com.westcatr.vcms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 * 上传资源表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private VcmsProperties vcmsProperties;

    @Autowired
    private IUserUtil iUserUtil;

    @Autowired
    private ScreenInfoMapper screenInfoMapper;

    @Autowired
    private ScreenInfoService screenInfoService;

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    WebSocketMsgAop socketMsg;

    @Override
    public IPage<File> ipage(FileQuery query) {
        return this.page(query.page(), new WrapperFactory<File>().create(query));
    }

    @Override
    public File isave(String fileOldPath, String fileName, String remake, MultipartFile multipartFile)
            throws Exception {
        fileName = fileName == null ? multipartFile.getName() : fileName;
        String saveFileName = vcmsProperties.getUploadPath() + fileName;// 保存路径名
        java.io.File fileSave = new java.io.File(saveFileName);// 拿到这个文件
        if (multipartFile != null) {// 如果是直接上传文件
            saveFileName = UploadUtil.uploadMutiFile(vcmsProperties.getUploadPath(), fileName, multipartFile);// 文件保存本地，返回保存路径.
        } else {// 如果是分片上传文件
            if (!FileUtil.exist(fileOldPath)) {
                throw new MyRuntimeException("未找到文件");
            }
            java.io.File oldFile = new java.io.File(fileOldPath);
            FileUtil.move(oldFile, fileSave, true);// 把分片上传合并的文件转移到我们的上传目录
            // String saveFileName = UploadUtil.upload(vcmsProperties.getUploadPath(),
            // multipartFile);
        }
        File file = new File();
        String fileType = tfFileType(FileUtil.extName(saveFileName), saveFileName);// 拿文件类型
        file.setFileName(fileName);
        file.setFileType(fileType);
        file.setFileSize(FileUtil.size(fileSave));
        file.setFilePath(fileName);// 他数据库设置的path和名字一样
        file.setRemarkInfo(remake);
        file.setUserName(iUserUtil.getIUser().getFullName());
        this.save(file);
        handleToPic(saveFileName, fileType);// 如果是ppt，pdf用来切割成一张张图片
        return file;
    }

    /**
     * 传入ppt或者pdf的时候后台拆分为图片
     *
     * @param filePath
     * @param fileType
     */
    @Async
    private void handleToPic(String filePath, String fileType) {
        // 把pdf拆分多张图放在后台
        if (fileType.equals("PDF")) {
            String dirName = filePath.split("\\.")[0];
            FileUtil.mkdir(dirName);
            FileToFile.pdfPathToImagePaths(filePath, FileUtil.getName(filePath).split("\\.")[0]);
        } else if (fileType.equals("PPT")) {
            String extName = FileUtil.extName(filePath);
            String dirName = filePath.split("\\.")[0] + "";
            FileUtil.mkdir(dirName);
            if (StrUtil.equalsAny(extName, true, "pptx")) {
                FileToFile.doPPT2007toImage(filePath, dirName, new ArrayList<>());
            } else {
                FileToFile.doPPT2003toImage(filePath, dirName, new ArrayList<>());
            }
        }
    }

    private String tfFileType(String fileExt, String filePath) {
        if (StrUtil.equalsAny(fileExt, true, "mp4", "avi", "mov", "mpeg", "mpg", "wmv", "rm", "rmvb", "flv", "3gp")) {
            return "视频";
        } else if (StrUtil.equalsAny(fileExt, true, "pptx", "ppt", "potx", "pps")) {
            return "PPT";
        } else if (StrUtil.equalsAny(fileExt, true, "cda", "wav", "mp3", "wma", "flac")) {
            return "音频";
        } else if (StrUtil.equalsAny(fileExt, true, "pdf", "ppt", "potx", "pps")) {
            return "PDF";
        } else if (StrUtil.equalsAny(fileExt, true, "jpg", "jpeg", "png", "bmp", "gif", "tif", "svg")) {
            return "图片";
        } else {
            FileUtil.del(filePath);
            throw new MyRuntimeException("上传文件格式不支持");
        }
    }

    @Override
    public File iupdate(MultipartFile multipartFile, String fileName, String remake, String id) throws Exception {
        File file = this.getById(id); 
        if (file== null) {
            throw new MyRuntimeException("msg");
        }
        String path = this.getById(id).getFilePath();
        FileUtil.del(vcmsProperties.getUploadPath() + path);
        String saveFileName = UploadUtil.upload(vcmsProperties.getUploadPath(), multipartFile);
        File param = this.getById(id);
        param.setFileName(fileName);
        param.setRemarkInfo(remake);
        param.setFileType(tfFileType(FileUtil.extName(saveFileName), vcmsProperties.getUploadPath() + saveFileName));
        param.setFileSize(multipartFile.getSize());
        param.setFilePath(saveFileName);
        param.setUserName(iUserUtil.getIUser().getFullName());
        this.updateById(param);
        return param;
    }

    @Override
    public FilePreviewVo igetById(String id) {
        File file = this.getById(id);
        FilePreviewVo filePreviewVo = new FilePreviewVo();
        BeanUtil.copyProperties(file, filePreviewVo);
        if (StrUtil.equalsAny(file.getFileType(), true, "PDF", "PPT", "视频")) {
            filePreviewVo.setAttributeInfo(fileExtendPath(file.getFileType(), file.getFilePath()));
        }
        return filePreviewVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean iremove(String id) {
        String path = this.getById(id).getFilePath();
        this.removeById(id);
        // 删除文件
        FileUtil.del(vcmsProperties.getUploadPath() + path);
        // 删除文件夹
        FileUtil.del(vcmsProperties.getUploadPath() + path.split("\\.")[0]);
        // 从file和fileGroup中间库删除该文件的所有数据
        fileMapper.deleteFromMid(id);
        // 更新屏幕信息
        List<ScreenInfo> screenInfo = screenInfoService.list(new QueryWrapper<ScreenInfo>().eq("resource_id", id));
        if (screenInfo != null) {
            screenInfo.forEach(scr -> screenInfoMapper.updateResource(scr.getId()));
        }
        return true;
    }

    /**
     * 获取某个文件的附属文件地址
     */
    @Override
    public List<String> fileExtendPath(String fileType, String filePath) {
        List<String> pptAndPdfFiles = new ArrayList<>();
        if (fileType.equals("视频")) {
            String videoPicPath = filePath.split("\\.")[0] + ".png";
            if (FileUtil.exist(videoPicPath)) {
                pptAndPdfFiles.add(videoPicPath);
            }
        } else {// ppt或者pdf
            String pPath = (vcmsProperties.getUploadPath() + filePath).split("\\.")[0];
            // 对文件按名称进行排序
            java.io.File[] files = new java.io.File(pPath).listFiles();
            if (null != files && files.length > 0) {
                List<java.io.File> filesList = Arrays.asList(files);
                filesList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                // 加入集合
                filesList.forEach(file -> pptAndPdfFiles.add(file.getName().split("-")[0] + "/" + file.getName()));
            }
        }
        return pptAndPdfFiles;
    }

    @Override
    public boolean addUrl(UrlAddParam urlAddParam) {
        File file = new File();
        file.setFileName(urlAddParam.getAppDescription());
        file.setFileType("URL");
        file.setFilePath(urlAddParam.getUrlPath());
        file.setUserName(iUserUtil.getIUser().getFullName());
        boolean save = this.save(file);
        return save;
    }

    @Override
    public List<File> listFileByGroupId(String id) {
        return fileMapper.listFileByGroupId(id);
    }

    @Override
    public void removeGroup(String id) {
        fileMapper.removeGroup(id);
    }

    @Override
    public void addGroup(String id, String fileId, Integer intervalTime) {
        if (ObjectUtil.isNotNull(intervalTime)) {
            fileMapper.addGroup(id, fileId, intervalTime);
        } else {
            fileMapper.addGroup(id, fileId, null);
        }
    }

    @Override
    public boolean updateUrl(UrlAddParam urlAddParam) {
        int count = this.count(new QueryWrapper<File>().eq("id", urlAddParam.getId()));
        if (count < 1) {
            throw new MyRuntimeException("数据库不存在该文件");
        }
        File file = new File();
        file.setId(urlAddParam.getId());
        file.setFileName(urlAddParam.getAppDescription());
        file.setFilePath(urlAddParam.getUrlPath());
        fileMapper.updateById(file);
        return true;
    }
}
