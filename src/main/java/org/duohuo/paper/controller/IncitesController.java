package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.annotation.KeyOperation;
import org.duohuo.paper.annotation.RequestLimit;
import org.duohuo.paper.facade.IncitesFacade;
import org.duohuo.paper.model.dto.DownloadDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(description = "被引频次信息接口", value = "被引频次信息")
@RequestMapping("/incites")
@RestController
public class IncitesController {

    @Resource(name = "incitesFacade")
    private IncitesFacade incitesFacade;

    @RequestLimit(count = 10)
    @ApiOperation(value = "被引频次下载", notes = "直接下载")
    @PostMapping(value = "/download")
    @RequiresAuthentication
    public ResponseEntity<byte[]> incitesDownload(@ApiParam("下载的具体条目") @RequestBody DownloadDto downloadDto) {
        return incitesFacade.downloadFacade(downloadDto);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "被引频次条件搜索", notes = "搜索条件有关键词和学科名称")
    @Cacheable(value = "incites_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/all")
    @RequiresAuthentication
    public JsonResult incitesSearch(@RequestBody SearchDto searchDto) {
        return incitesFacade.searchFacade(searchDto);
    }

    @KeyOperation(operation = KeyOperation.Operation.UPLOAD)
    @ApiOperation(value = "被引频次上传", notes = "被引频次上传，上传一个excel")
    @PostMapping(value = "/upload")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadIncitesExcel(@RequestParam("file") MultipartFile file) {
        return incitesFacade.uploadFacade(file);
    }

}
