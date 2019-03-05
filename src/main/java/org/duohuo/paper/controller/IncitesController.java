package org.duohuo.paper.controller;

import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.duohuo.paper.constants.SearchTypeConstant;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.dto.DownloadDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.IncitesService;
import org.duohuo.paper.utils.DownloadUtil;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.duohuo.paper.Constants.DATE_FORMAT;

@Api(description = "被引频次信息接口", value = "被引频次信息")
@RequestMapping("/incites")
@RestController
public class IncitesController {

    @Resource(name = "incitesServiceImpl")
    private IncitesService incitesService;

    @ApiOperation(value = "被引频次下载", notes = "直接下载")
    @PostMapping(value = "/download")
    @RequiresAuthentication
    public ResponseEntity<byte[]> journalDownload(@ApiParam("下载的具体条目") @RequestBody DownloadDto downloadDto) throws Exception {
        List<Integer> incitesId = downloadDto.getData().stream().map(Long::intValue).collect(Collectors.toList());
        List<BaseRowModel> downloadExcelModels = incitesService.getDownByIdList(incitesId);
        if (!ObjectUtil.ifNotNullList(downloadExcelModels)) {
            throw new NotFoundException("incites的id不正确");
        }
        byte[] bytes = ExcelUtil.getDownByte(downloadExcelModels, 3);
        String fileName = Integer.toString(("INCITES " + DATE_FORMAT.format(new Date())).hashCode());
        return DownloadUtil.getResponseEntity(fileName, bytes);
    }

    @ApiOperation(value = "被引频次条件搜索", notes = "搜索条件有关键词和学科名称")
    @Cacheable(value = "incites_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/all")
    @RequiresAuthentication
    public JsonResult incitesSearch(@RequestBody SearchDto searchDto) {
        Integer page = searchDto.getPage();
        Boolean ifDesc = searchDto.getIfDesc();
        if (page == null) page = 1;
        if (ifDesc == null) ifDesc = false;
        String keyWord = searchDto.getKeyWord();
        String keyWordType = searchDto.getKeyWordType();
        List<Integer> subjectList = searchDto.getConditionData().getSubject();
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            if (!ObjectUtil.ifNotNullList(subjectList)) {
                return incitesService.searchByNone(page, ifDesc);
            } else {
                return incitesService.searchByCategory(page, ifDesc, subjectList);
            }
        } else {
            if (!ObjectUtil.ifNotNullList(subjectList)) {
                switch (keyWordType) {
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                        return incitesService.searchByAccessionNumber(page, ifDesc, keyWord);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                        return incitesService.searchByArticleName(page, ifDesc, keyWord);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                        return incitesService.searchByDoi(page, ifDesc, keyWord);
                    default:
                        return new JsonResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
                }
            } else {
                switch (keyWordType) {
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                        return incitesService.searchByAccessionNumberAndCategory(page, ifDesc, keyWord, subjectList);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                        return incitesService.searchByArticleNameAndCategory(page, ifDesc, keyWord, subjectList);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                        return incitesService.searchByDoiAndCategory(page, ifDesc, keyWord, subjectList);
                    default:
                        return new JsonResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
                }
            }
        }
    }
}
