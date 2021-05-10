package com.westcatr.vcms.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.rd.base.bweb.exception.MyRuntimeException;
import com.westcatr.vcms.config.VcmsProperties;
import com.westcatr.vcms.controller.query.FileQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.westcatr.vcms.service.FileService;
import com.westcatr.vcms.util.DownloadUtil;
import com.westcatr.vcms.util.FileType;
import com.westcatr.vcms.util.RedioUtil;
import com.westcatr.vcms.entity.File;
import com.westcatr.vcms.param.FileAddParam;
import com.westcatr.vcms.param.FilePreviewVo;
import com.westcatr.vcms.param.FileSlice;
import com.westcatr.vcms.param.FileSliceConfirm;
import com.westcatr.vcms.param.UrlAddParam;
import com.westcatr.rd.base.acommon.annotation.IPermissions;
import com.westcatr.rd.base.acommon.annotation.SaveLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import com.westcatr.rd.base.acommon.vo.IResult;
import lombok.extern.slf4j.Slf4j;
import static cn.hutool.core.util.StrUtil.COMMA;

/**
 * @description : File 控制器 ---------------------------------
 * @author admin
 * @since 2020-05-15
 */

@Api(value = "File接口[file]", tags = "上传资源表接口")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private VcmsProperties vcmsProperties;

    @Value("${vcms.upload-path.slice}")
    private String sliceTmp;



    /**
     * @description : 获取分页列表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "上传资源表分页数据接口", module = "上传资源表管理")
    @IPermissions(value = "file:page")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "上传资源表分页数据接口[file:page]", nickname = "file:page")
    @GetMapping("/page")
    public IResult<IPage<File>> getFilePage(FileQuery query) {
        return IResult.ok(fileService.ipage(query));
    }


   /**
    * @description : 文件直接上传资源表 ---------------------------------
    * @author : admin
    * @since : Create in 2020-05-15
    */
   @SaveLog(value = "新增文件资源表数据接口", level = 2, module = "上传资源表管理")
   // @IPermissions(value="file:add")
   @ApiOperationSupport(order = 3)
   @ApiOperation(value = "新增文件资源表数据接口[file:addFile]", nickname = "file:addFile")
   @PostMapping("/addFile")
   public IResult<File> addFile(FileAddParam fileAddParam) throws Exception {
      //文件不能重名
      int count = fileService.count(new QueryWrapper<File>().eq("file_name", fileAddParam.getFileName()));
      if (count>0){
         throw   new MyRuntimeException("文件名已存在,请修改文件名");
      }
      return IResult.ok(
              fileService.isave(null, fileAddParam.getFileName(), fileAddParam.getRemake(),fileAddParam.getFile()));
   }

    @ApiOperation(value = "大文件分片上传")
    @PostMapping("/slice/upload")
    public IResult upload(FileSlice fileSlice) {
        java.io.File dir = new java.io.File(sliceTmp + "/" + fileSlice.getId());
        if (!dir.isDirectory()) {
            dir.delete();
            dir.mkdirs();
        }
        MultipartFile multipartFile = fileSlice.getFile();
        String fileTmpName = String.valueOf(fileSlice.getSeq());
        java.io.File dest = new java.io.File(dir, fileTmpName);
        try {
            multipartFile.transferTo(dest);
            return IResult.ok("分片上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IResult.fail("分片上传失败");
    }

    @ApiOperation(value = "分片上传完成标识接口")
    @PostMapping("/slice/confirm")
    public IResult upload(FileSliceConfirm confirm) throws FileNotFoundException {

        if (confirm == null || StrUtil.isEmpty(confirm.getId()) || StrUtil.isEmpty(confirm.getName())
                || confirm.getSliceLength() == null || confirm.getByteLength() == null) {
            return IResult.fail("参数不全");
        }
        java.io.File dir = new java.io.File(sliceTmp + "/" + confirm.getId());
        if (!dir.isDirectory()) {
            return IResult.fail("upload:缓存目录不存在");
        }
        try {
            java.io.File[] files = dir.listFiles();
            if (files.length != confirm.getSliceLength()) {
                throw new RuntimeException("upload:分片个数不对");
            }
            String filePath = FileType.fileType(confirm.getName());
            java.io.File resultDir = new java.io.File(vcmsProperties.getUploadPath() + filePath);
            if (!resultDir.isDirectory()) {
                resultDir.delete();
                resultDir.mkdirs();
            }
            java.io.File resultFile = new java.io.File(resultDir, confirm.getName());
            long totalByteLength = Arrays.stream(files).map(file -> file.length()).reduce(0L, (x, y) -> x + y);
            if (totalByteLength != confirm.getByteLength()) {
                throw new RuntimeException("upload:文件大小不一致");
            }
            Arrays.stream(files).sorted(Comparator.comparing(f -> Integer.valueOf(f.getName()))).forEach(file -> {
                byte[] bytes = FileUtil.readBytes(file);
                FileUtil.writeBytes(bytes, resultFile, 0, bytes.length, true);
            });
            return IResult.ok("上传成功", resultFile.getPath() /* "/files/"+filePath+"/"+confirm.getName() */);
        } catch (Exception e) {
            String message = e.getMessage() == null ? "error"
                    : e.getMessage().startsWith("upload") ? e.getMessage() : "error";
            return IResult.fail(message);
        } finally {
            dir.delete();
        }
    }

    /**
     * @description : 通过id获取上传资源表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "根据id获取单个资源", module = "上传资源表管理")
    @IPermissions(value = "file:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "根据id获取单个资源", notes = "file:get")
    @GetMapping("/getFileById")
    public IResult<FilePreviewVo> getFileById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        return IResult.ok(fileService.igetById(id));
    }

    /**
     * @description : 通过id获取上传资源表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @ResponseBody
    @SaveLog(value = "根据id对文件进行下载", module = "上传资源表管理")
    // @IPermissions(value = "file:get")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "根据id对文件进行下载", notes = "file:get")
    @GetMapping("/downFileById")
    public void downFileById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = fileService.igetById(id);
        if (StrUtil.containsAny(file.getFilePath(), "mp4", "avi", "mov", "mpeg", "mpg", "wmv", "rm", "rmvb", "flv",
                "3gp","mp3")) {
            String downloadDir = vcmsProperties.getUploadPath();
            RedioUtil.player(downloadDir + file.getFilePath(), request, response);
        } else {
            download(file.getFilePath(), file.getFileName(), response);
        }

    }

    @ResponseBody
    @GetMapping("/{filepath}")
    @ApiOperation(value = "根据传入的文件路径下载", notes = "下载文件")
    public void downloadPath(@PathVariable("filepath") String filepath,HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = fileService.getOne(new QueryWrapper<File>().eq("file_path", filepath));
        if (StrUtil.containsAny(file.getFilePath(), "mp4", "avi", "mov", "mpeg", "mpg", "wmv", "rm", "rmvb", "flv",
                "3gp")) {
            String downloadDir = vcmsProperties.getUploadPath();
            RedioUtil.player(downloadDir + file.getFilePath(), request, response);
        } else {
            download(file.getFilePath(), file.getFileName(), response);
        }
    }

    /**
     * 下载文件
     */
    @ResponseBody
    @GetMapping("/")
    @ApiOperation(value = "根据文件原始路径下载文件", notes = "下载文件")
    public void download(@RequestParam(required = true) String downloadPath,
            @RequestParam(required = false) String realFileName, HttpServletResponse response) throws Exception {
        // 下载目录，既是上传目录
        String downloadDir = vcmsProperties.getUploadPath();
        // 文件下载，使用默认下载处理器
        // 文件下载，使用自定义下载处理器
        DownloadUtil.download(downloadDir, downloadPath, response, realFileName,
                (dir, fileName, file, fileExtension, contentType, length) -> {
                    return true;
                });
    }

    /**
     * @description : 新增分片上传资源表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "新增分片上传资源表数据接口", level = 2, module = "分片上传资源表管理")
    // @IPermissions(value="file:add")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增分片上传资源表数据接口[file:add]", nickname = "file:add")
    @PostMapping("/add")
    public IResult<File> add(@RequestBody FileAddParam fileAddParam) throws Exception {
        //文件不能重名
        int count = fileService.count(new QueryWrapper<File>().eq("file_name", fileAddParam.getFileName()));
        if (count>0){
          throw   new MyRuntimeException("文件名已存在,请修改文件名");
        }
        return IResult.ok(
                fileService.isave(fileAddParam.getFilePath(), fileAddParam.getFileName(), fileAddParam.getRemake(),null));
    }

    /**
     * @description : 更新上传资源表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "更新上传资源表数据接口", level = 2, module = "上传资源表管理")
    @IPermissions(value = "file:update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新上传资源表数据接口[file:update]", nickname = "file:update")
    @PostMapping("/update")
    public IResult<File> updateFileById(@RequestParam("file") MultipartFile multipartFile,
            @RequestParam("fileName") String fileName, @RequestParam(value = "remake", required = false) String remake,
            @RequestParam(value = "id", required = true) String id) throws Exception {
        return IResult.ok(fileService.iupdate(multipartFile, fileName, remake, id));
    }

    /**
     * @description : 通过id删除上传资源表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "删除上传资源表数据接口", level = 3, module = "上传资源表管理")
    @IPermissions(value = "file:del")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除上传资源表数据接口[file:del]", nickname = "file:del")
    @DeleteMapping("/delete")
    public IResult deleteFileById(@NotNull(message = "id不能为空") @RequestParam(value = "id") String id) {
        for (String s : id.split(COMMA)) {
            fileService.iremove(s);
        }
        return IResult.ok();
    }

    /**
     * @description : 新增Url资源表 ---------------------------------
     * @author : admin
     * @since : Create in 2020-05-15
     */
    @SaveLog(value = "新增URL数据接口", level = 2, module = "上传资源表管理")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增URL数据接口[file:urladd]", nickname = "file:urladd")
    @PostMapping("/urlAdd")
    public IResult urlAdd(@RequestBody UrlAddParam urlAddParam) {
        return IResult.auto(fileService.addUrl(urlAddParam));
    }


   /**
    * @description : 修改Url文件 ---------------------------------
    * @author : admin
    * @since : Create in 2020-05-15
    */
   @SaveLog(value = "更新URL数据接口", level = 2, module = "上传资源表管理")
   @ApiOperationSupport(order = 3)
   @ApiOperation(value = "更新URL数据接口[file:urlUpdate]", nickname = "file:urlUpdate")
   @PostMapping("/urlUpdate")
   public IResult urlUpdate(@RequestBody UrlAddParam urlAddParam) {
      if (urlAddParam.getId()==null){
        return IResult.fail("Url修改失败,请传入正确参数!");
      }
      return IResult.auto(fileService.updateUrl(urlAddParam));
   }
}