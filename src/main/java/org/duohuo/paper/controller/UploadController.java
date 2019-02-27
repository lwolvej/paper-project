package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.exceptions.CustomException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.BaseLineService;
import org.duohuo.paper.service.JournalExcelService;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

@Api(description = "文件上传接口", value = "文件上传")
@RequestMapping("/upload")
@RestController
public class UploadController {

    @Resource(name = "journalExcelService")
    private JournalExcelService journalExcelService;

    @Resource(name = "baseLineServiceImpl")
    private BaseLineService baseLineService;

    @ApiOperation(value = "期刊上传", notes = "期刊上传，上传一个excel")
    @PostMapping(value = "/journal")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadJournalExcel(@RequestParam("file") MultipartFile file,
                                         @RequestParam("year") Integer year,
                                         @RequestParam("month") Integer month) throws Exception {
        if (year == null) {
            throw new NotFoundException("journal上传时，年为空!");
        }
        if (month == null) {
            throw new NotFoundException("journal上传时，月为空!");
        }
        InputStream stream = file.getInputStream();
        if (RegexUtil.excelFileValidation(file.getName())) {
            throw new CustomException("期刊上传的文件不规范! " + file.getName());
        }
        return journalExcelService.insertJournalExcel(stream, year, month, file.getName());
    }

    @ApiOperation(value = "基准线上传", notes = "基准线上传，上传一个excel")
    @PostMapping(value = "/baseLine")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadBaseLineExcel(@RequestParam("file") MultipartFile file) throws Exception {
        InputStream stream = file.getInputStream();
        if (RegexUtil.excelFileValidation(file.getName())) {
            throw new CustomException("基准线上传的文件不规范! " + file.getName());
        }
        return baseLineService.insertBaseLineData(stream, file.getName());
    }
}
