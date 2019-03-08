package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.annotation.KeyOperation;
import org.duohuo.paper.annotation.RequestLimit;
import org.duohuo.paper.facade.JournalFacade;
import org.duohuo.paper.model.dto.*;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author lwolvej
 */
@Api(description = "esi信息接口", value = "esi信息")
@RequestMapping("/journal")
@RestController
public class JournalController {

    @Resource(name = "journalFacade")
    private JournalFacade journalFacade;

    @KeyOperation(operation = KeyOperation.Operation.DELETE)
    @ApiOperation(value = "删除期刊某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult journalDelete(@RequestBody DeleteDto deleteDto) {
        return journalFacade.journalDeleteFacade(deleteDto);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "journal跌出")
    @Cacheable(value = "journal_search_fell", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/fellOut")
    @RequiresAuthentication
    public JsonResult searchFellOut(@ApiParam("搜寻条件") @RequestBody SearchDto2 searchDto2) {
        return journalFacade.searchFellOutFacade(searchDto2);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "journal新增")
    @Cacheable(value = "journal_search_new", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/newAddition")
    @RequiresAuthentication
    public JsonResult searchNewAddition(@ApiParam("搜寻条件") @RequestBody SearchDto2 searchDto2) {
        return journalFacade.searchNewAdditionFacade(searchDto2);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "当前")
    @Cacheable(value = "journal_search_current", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/current")
    @RequiresAuthentication
    public JsonResult current(@ApiParam("当前期搜寻") @RequestBody SearchDto2 searchDto2) {
        return journalFacade.currentFacade(searchDto2);
    }

    @RequestLimit(count = 10)
    @ApiOperation(value = "journal下载", notes = "直接下载")
    @PostMapping(value = "/download")
    @RequiresAuthentication
    public ResponseEntity<byte[]> journalDownload(@ApiParam("下载的具体条目") @RequestBody DownloadDto downloadDto) {
        return journalFacade.journalDownloadFacade(downloadDto);
    }

    @RequestLimit(count = 20)
    @ApiOperation(value = "期刊信息条件搜索", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "journal_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/all")
    @RequiresAuthentication
    public JsonResult esiSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        return journalFacade.esiSearchFacade(searchDto);
    }

    @KeyOperation(operation = KeyOperation.Operation.UPLOAD)
    @ApiOperation(value = "期刊上传", notes = "期刊上传，上传一个excel")
    @PostMapping(value = "/upload")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult uploadJournalExcel(@RequestParam("file") MultipartFile file,
                                         @RequestParam("year") Integer year,
                                         @RequestParam("month") Integer month) {
        return journalFacade.uploadFacade(file, year, month);
    }
}
