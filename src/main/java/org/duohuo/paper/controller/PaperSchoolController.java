package org.duohuo.paper.controller;

import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.excel.model.download.PaperExcelDownloadModel;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.SchoolPaperImage;
import org.duohuo.paper.model.dto.DeleteDto;
import org.duohuo.paper.model.dto.DownloadDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.PaperSearchService;
import org.duohuo.paper.service.SchoolPaperImageService;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.duohuo.paper.Constants.DATE_FORMAT;


@Api(description = "论文查询接口", value = "论文查询")
@RequestMapping("/ourPaper")
@RestController
public class PaperSchoolController extends PaperController {

    @Resource(name = "paperHighlySchoolSearchService")
    private PaperSearchService paperHighlySchoolSearchService;

    @Resource(name = "paperHotSchoolSearchService")
    private PaperSearchService paperHotSchoolSearchService;

    @Resource(name = "schoolPaperImageServiceImpl")
    private SchoolPaperImageService schoolPaperImageServiceImpl;

    @ApiOperation(value = "下载我校论文信息", notes = "下载一个zip，包括图片和excel，在swagger无法作出正常下载")
    @PostMapping(value = "/download")
    @RequiresAuthentication
    public void download(@RequestBody DownloadDto downloadDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (downloadDto == null) {
            throw new NotFoundException("缺少下载数据");
        }
        List<Long> paperIdList = downloadDto.getData();
        if (!ObjectUtil.ifNotNullList(paperIdList)) {
            throw new NotFoundException("缺少下载数据");
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
        String fileName = Integer.toString(("PAPER " + DATE_FORMAT.format(new Date())).hashCode());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".zip");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream");
        List<SchoolPaperImage> schoolPaperImages = schoolPaperImageServiceImpl.getAllByPaperId(paperIdList);
        for (SchoolPaperImage image : schoolPaperImages) {
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setSourceExternalStream(true);
            zipParameters.setFileNameInZip(image.getImageName());
            zipOutputStream.putNextEntry(null, zipParameters);
            zipOutputStream.write(image.getImageData());
            zipOutputStream.closeEntry();
        }
        List<BaseRowModel> paperList = paperHighlySchoolSearchService.getPaperListById(paperIdList)
                .stream().map(PaperExcelDownloadModel::new).collect(Collectors.toList());
        byte[] bytes = ExcelUtil.getDownByte(paperList, 1);
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setSourceExternalStream(true);
        zipParameters.setFileNameInZip("data.xlsx");
        zipOutputStream.putNextEntry(null, zipParameters);
        zipOutputStream.write(bytes);
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        zipOutputStream.flush();
    }

    @ApiOperation(value = "删除我校高被引某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/highlyCited/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperHighlyCitedDelete(@RequestBody DeleteDto deleteDto) {
        if (deleteDto == null) {
            throw new NotFoundException("本校高被引删除信息不完整");
        }
        if (deleteDto.getMonth() == null || deleteDto.getYear() == null) {
            throw new NotFoundException("本校高被引删除信息不完整");
        }
        return getJsonResult(deleteDto.getYear(), deleteDto.getMonth(), paperHighlySchoolSearchService);
    }

    @ApiOperation(value = "删除esi热点某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/hot/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperHotDelete(@RequestBody DeleteDto deleteDto) {
        if (deleteDto == null) {
            throw new NotFoundException("本校热点删除信息不完整");
        }
        if (deleteDto.getMonth() == null || deleteDto.getYear() == null) {
            throw new NotFoundException("本校热点删除信息不完整");
        }
        return getJsonResult(deleteDto.getYear(), deleteDto.getMonth(), paperHotSchoolSearchService);
    }

    @ApiOperation(value = "本校高被引", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_school_highly_cited_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/highlyCited")
    @RequiresAuthentication
    public JsonResult paperHighlyCitedSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        if (searchDto == null) {
            throw new NotFoundException("School highly cited Paper搜寻条件为空!");
        }
        return getJsonResult(searchDto, paperHighlySchoolSearchService);
    }

    @ApiOperation(value = "本校热点", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_school_hot_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/hotPaper")
    @RequiresAuthentication
    public JsonResult paperHotSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        if (searchDto == null) {
            throw new NotFoundException("School hot Paper搜寻条件为空!");
        }
        return getJsonResult(searchDto, paperHotSchoolSearchService);
    }
}
