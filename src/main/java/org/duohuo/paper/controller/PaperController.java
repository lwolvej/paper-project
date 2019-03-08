package org.duohuo.paper.controller;

import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.annotation.KeyOperation;
import org.duohuo.paper.annotation.RequestLimit;
import org.duohuo.paper.annotation.Upload;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.exceptions.ZipFileException;
import org.duohuo.paper.facade.PaperFacade;
import org.duohuo.paper.model.SchoolPaperImage;
import org.duohuo.paper.model.dto.DeleteDto;
import org.duohuo.paper.model.dto.DownloadDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Api(description = "论文查询接口", value = "论文查询")
@RequestMapping("/paper")
@RestController
public class PaperController {

    @Resource(name = "paperFacade")
    private PaperFacade paperFacade;

    @KeyOperation(operation = KeyOperation.Operation.DELETE)
    @ApiOperation(value = "删除esi高被引某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/esi/highlyCited/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperEsiHighlyCitedDelete(@RequestBody DeleteDto deleteDto) {
        return paperFacade.deletePaperFacade(deleteDto, 1);
    }

    @KeyOperation(operation = KeyOperation.Operation.DELETE)
    @ApiOperation(value = "删除esi热点某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/esi/hot/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperEsiHotDelete(@RequestBody DeleteDto deleteDto) {
        return paperFacade.deletePaperFacade(deleteDto, 2);
    }

    @KeyOperation(operation = KeyOperation.Operation.DELETE)
    @ApiOperation(value = "删除我校高被引某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/school/highlyCited/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperSchoolHighlyCitedDelete(@RequestBody DeleteDto deleteDto) {
        return paperFacade.deletePaperFacade(deleteDto, 3);
    }

    @KeyOperation(operation = KeyOperation.Operation.DELETE)
    @ApiOperation(value = "删除esi热点某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/school/hot/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperSchoolHotDelete(@RequestBody DeleteDto deleteDto) {
        return paperFacade.deletePaperFacade(deleteDto, 4);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "esi高被引", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_highly_cited_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/esi/highlyCited/search")
    @RequiresAuthentication
    public JsonResult paperEsiHighlyCitedSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        return paperFacade.paperSearchFacade(searchDto, 1);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "esi热点", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_hot_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/esi/hot/search")
    @RequiresAuthentication
    public JsonResult paperEsiHotSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        return paperFacade.paperSearchFacade(searchDto, 2);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "本校高被引", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_school_highly_cited_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/school/highlyCited/search")
    @RequiresAuthentication
    public JsonResult paperSchoolHighlyCitedSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        return paperFacade.paperSearchFacade(searchDto, 3);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "本校热点", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_school_hot_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/school/hotPaper/search")
    @RequiresAuthentication
    public JsonResult paperSchoolHotSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        return paperFacade.paperSearchFacade(searchDto, 4);
    }

    @KeyOperation(operation = KeyOperation.Operation.UPLOAD)
    @Upload
    @ApiOperation(value = "esi论文高被引上传", notes = "esi高被引论文上传, 上传一个zip压缩包")
    @PostMapping(value = "/esi/highlyCited/upload")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadEsiPaperHighlyZip(@RequestParam("file") MultipartFile file,
                                              @RequestParam("year") Integer year,
                                              @RequestParam("month") Integer month) {
        return paperFacade.uploadFileFacade(file, year, month, 1);
    }

    @KeyOperation(operation = KeyOperation.Operation.UPLOAD)
    @Upload
    @ApiOperation(value = "esi论文热点论文上传", notes = "esi热点论文上传，上传一个zip压缩包")
    @PostMapping(value = "/esi/hot/upload")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadEsiPaperHotZip(@RequestParam("file") MultipartFile file,
                                           @RequestParam("year") Integer year,
                                           @RequestParam("month") Integer month) {
        return paperFacade.uploadFileFacade(file, year, month, 2);
    }

    @KeyOperation(operation = KeyOperation.Operation.UPLOAD)
    @Upload
    @ApiOperation(value = "本校高被引论文上传", notes = "本校高被引论文上传，上传一个zip包")
    @PostMapping("/school/highlyCited/upload")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadSchoolPaperHighlyZip(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("year") Integer year,
                                                 @RequestParam("month") Integer month) {
        return paperFacade.uploadFileFacade(file, year, month, 3);
    }

    @KeyOperation(operation = KeyOperation.Operation.UPLOAD)
    @Upload
    @ApiOperation(value = "本校热点论文上传", notes = "本校热点论文上传, 上传一个zip包")
    @PostMapping("/school/hot/upload")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadSchoolPaperHotZip(@RequestParam("file") MultipartFile file,
                                              @RequestParam("year") Integer year,
                                              @RequestParam("month") Integer month) {
        return paperFacade.uploadFileFacade(file, year, month, 4);
    }

    @RequestLimit(count = 10)
    @ApiOperation(value = "esi论文下载", notes = "直接下载")
    @PostMapping(value = "/esi/download")
    @RequiresAuthentication
    public ResponseEntity<byte[]> esiPaperDownload(@ApiParam("下载的具体条目") @RequestBody DownloadDto downloadDto) {
        return paperFacade.downloadEsiPaperFacade(downloadDto);
    }

    @RequestLimit(count = 10)
    @ApiOperation(value = "下载我校论文信息", notes = "下载一个zip，包括图片和excel，在swagger无法作出正常下载")
    @PostMapping(value = "/school/download")
    @RequiresAuthentication
    public void schoolPaperDownload(@RequestBody DownloadDto downloadDto, HttpServletResponse response) {
        if (downloadDto == null) {
            throw new NotFoundException("缺少下载数据");
        }
        List<Long> paperIdList = downloadDto.getData();
        if (!ObjectUtil.ifNotNullCollection(paperIdList)) {
            throw new NotFoundException("缺少下载数据");
        }
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
            String fileName = Integer.toString(("PAPER " + new Date()).hashCode());
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".zip");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/zip");
            List<SchoolPaperImage> schoolPaperImages = paperFacade.getImageByIdList(paperIdList);
            for (SchoolPaperImage image : schoolPaperImages) {
                ZipParameters zipParameters = new ZipParameters();
                zipParameters.setSourceExternalStream(true);
                zipParameters.setFileNameInZip(image.getImageName());
                zipOutputStream.putNextEntry(null, zipParameters);
                zipOutputStream.write(image.getImageData());
                zipOutputStream.closeEntry();
            }
            List<BaseRowModel> paperList = paperFacade.getDownloadModel(paperIdList);
            byte[] bytes = ExcelUtil.getDownByte(paperList, 1);
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setSourceExternalStream(true);
            zipParameters.setFileNameInZip("data.xlsx");
            zipOutputStream.putNextEntry(null, zipParameters);
            zipOutputStream.write(bytes);
            zipOutputStream.closeEntry();
            zipOutputStream.finish();
        } catch (IOException | ZipException e) {
            throw new ZipFileException("压缩文件出现异常:" + e.getMessage());
        }
    }
}
