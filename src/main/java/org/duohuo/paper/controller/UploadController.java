package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.Constants;
import org.duohuo.paper.exceptions.CustomException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.BaseLineService;
import org.duohuo.paper.service.JournalExcelService;
import org.duohuo.paper.service.PaperExcelService;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.FileUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@Api(description = "文件上传接口", value = "文件上传")
@RequestMapping("/upload")
@RestController
public class UploadController {

    @Resource(name = "journalExcelServiceImpl")
    private JournalExcelService journalExcelService;

    @Resource(name = "baseLineServiceImpl")
    private BaseLineService baseLineService;

    @Resource(name = "paperExcelServiceImpl")
    private PaperExcelService paperExcelService;

    @ApiOperation(value = "esi论文热点论文上传", notes = "esi热点论文上传，上传一个zip压缩包")
    @PostMapping(value = "/paper/esi/hot")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadEsiPaperHotZip(@RequestParam("file") MultipartFile file,
                                           @RequestParam("year") Integer year,
                                           @RequestParam("month") Integer month) throws Exception {
        paperValidation(file, year, month);
        String jarPath = ExcelUtil.getJarPath();
        String resultPath = jarPath + File.separator + Constants.TEMP_FILE_ESI_HOT_PAPER;
        String fileName = file.getOriginalFilename();
        return paperExcelService.insertPaperExcel(FileUtil.decompressZipFile(file.getBytes(), jarPath, resultPath, fileName), year, month, 2, file.getOriginalFilename());
    }

    @ApiOperation(value = "esi论文高被引上传", notes = "esi高被引论文上传, 上传一个zip压缩包")
    @PostMapping(value = "/paper/esi/highly")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadEsiPaperHighlyZip(@RequestParam("file") MultipartFile file,
                                              @RequestParam("year") Integer year,
                                              @RequestParam("month") Integer month) throws Exception {
        paperValidation(file, year, month);
        String jarPath = ExcelUtil.getJarPath();
        String resultPath = jarPath + File.separator + Constants.TEMP_FILE_ESI_HIGHLY_PAPER;
        String fileName = file.getOriginalFilename();
        return paperExcelService.insertPaperExcel(FileUtil.decompressZipFile(file.getBytes(), jarPath, resultPath, fileName), year, month, 1, file.getOriginalFilename());
    }

    @ApiOperation(value = "本校热点论文上传", notes = "本校热点论文上传, 上传一个zip包")
    @PostMapping("/paper/school/hot")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadSchoolPaperHotZip(@RequestParam("file") MultipartFile file,
                                              @RequestParam("year") Integer year,
                                              @RequestParam("month") Integer month) throws Exception {
        paperValidation(file, year, month);
        String jarPath = ExcelUtil.getJarPath();
        String resultPath = jarPath + File.separator + Constants.TEMP_FILE_SCHOOL_HOT_PAPER;
        String fileName = file.getOriginalFilename();
        return paperExcelService.insertPaperExcel(FileUtil.decompressZipFile(file.getBytes(), jarPath, resultPath, fileName), year, month, 4, file.getOriginalFilename());
    }

    @ApiOperation(value = "本校高被引论文上传", notes = "本校高被引论文上传，上传一个zip包")
    @PostMapping("/paper/school/highly")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadSchoolPaperHighlyZip(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("year") Integer year,
                                                 @RequestParam("month") Integer month) throws Exception {
        paperValidation(file, year, month);
        String jarPath = ExcelUtil.getJarPath();
        String resultPath = jarPath + File.separator + Constants.TEMP_FILE_SCHOOL_HIGHLY_PAPER;
        String fileName = file.getOriginalFilename();
        return paperExcelService.insertPaperExcel(FileUtil.decompressZipFile(file.getBytes(), jarPath, resultPath, fileName), year, month, 4, file.getOriginalFilename());
    }


    @ApiOperation(value = "期刊上传", notes = "期刊上传，上传一个excel")
    @PostMapping(value = "/journal")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadJournalExcel(@RequestParam("file") MultipartFile file,
                                         @RequestParam("year") Integer year,
                                         @RequestParam("month") Integer month) throws Exception {
        if (file == null) {
            throw new NotFoundException("处理文件未上传");
        }
        if (year == null) {
            throw new NotFoundException("journal上传时，年为空!");
        }
        if (month == null) {
            throw new NotFoundException("journal上传时，月为空!");
        }
        String fileName = file.getOriginalFilename();
        if (!RegexUtil.excelFileValidation(fileName)) {
            throw new CustomException("期刊上传的文件不规范! " + fileName);
        }
        byte[] data = file.getBytes();
        return journalExcelService.insertJournalExcel(data, year, month, fileName);
    }

    @ApiOperation(value = "基准线上传", notes = "基准线上传，上传一个excel")
    @PostMapping(value = "/baseLine")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadBaseLineExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            throw new NotFoundException("处理文件未上传!");
        }
        String fileName = file.getOriginalFilename();
        if (!RegexUtil.excelFileValidation(fileName)) {
            throw new CustomException("基准线上传的文件不规范! " + fileName);
        }
        byte[] data = file.getBytes();
        return baseLineService.insertBaseLineData(data, fileName);
    }

    private void paperValidation(MultipartFile file, Integer year, Integer month) {
        if (file == null) {
            throw new NotFoundException("处理文件未上传");
        }
        if (year == null) {
            throw new NotFoundException("Paper上传时年为空!");
        }
        if (month == null) {
            throw new NotFoundException("Paper上传时月为空!");
        }
    }
}
