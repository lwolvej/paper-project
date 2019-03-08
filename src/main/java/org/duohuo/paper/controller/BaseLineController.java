package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.annotation.RequestLimit;
import org.duohuo.paper.facade.BaseLineFacade;
import org.duohuo.paper.model.dto.BaseLineSearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(description = "基准线信息接口", value = "基准线插入，查询")
@RequestMapping("/baseLine")
@RestController
public class BaseLineController {

    @Resource(name = "baseLineFacade")
    private BaseLineFacade baseLineFacade;

    @RequestLimit(count = 20)
    @ApiOperation(value = "按学科查询基准线")
    @PostMapping(value = "/byCategory")
    @RequiresAuthentication
    public JsonResult searchBySubjects(@RequestBody BaseLineSearchDto baseLineSearchDto) {
        return baseLineFacade.searchByCategoryList(baseLineSearchDto);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "查询基准线")
    @PostMapping(value = "/byAll")
    @RequiresAuthentication
    public JsonResult searchByAll() {
        return baseLineFacade.searchByAll();
    }

    @ApiOperation(value = "基准线上传", notes = "基准线上传，上传一个excel")
    @PostMapping(value = "/upload")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadBaseLineExcel(@RequestParam("file") MultipartFile file) {
        return baseLineFacade.uploadFacade(file);
    }
}
