package org.duohuo.paper.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.lingala.zip4j.io.ZipInputStream;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.excel.model.download.JournalDownloadModel;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.dto.*;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.JournalSearchService;
import org.duohuo.paper.utils.DownloadUtil;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.duohuo.paper.Constants.DATE_FORMAT;

/**
 * @author lwolvej
 */
@Api(description = "esi信息接口", value = "esi信息")
@RequestMapping("/journal")
@RestController
public class JournalController {

    @Resource(name = "journalSearchService")
    private JournalSearchService journalSearchService;


    @ApiOperation(value = "删除期刊某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult journalDelete(@RequestBody DeleteDto deleteDto) {
        if (deleteDto == null) {
            throw new NotFoundException("期刊删除信息不完整");
        }
        if (deleteDto.getMonth() == null || deleteDto.getYear() == null) {
            throw new NotFoundException("期刊删除信息不完整");
        }
        return journalSearchService.deleteByYearAndMonth(deleteDto.getYear(), deleteDto.getMonth());
    }

    @ApiOperation(value = "journal跌出")
    @Cacheable(value = "journal_search_fell", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/fellOut")
    @RequiresAuthentication
    public JsonResult searchFellOut(@ApiParam("搜寻条件") @RequestBody SearchDto2 searchDto2) {
        validation(searchDto2);
        return journalSearchService.getFellOut(searchDto2.getPageNum(), searchDto2.getIfDesc(), searchDto2.getKeyWord(), searchDto2.getSubject());
    }

    @ApiOperation(value = "journal新增")
    @Cacheable(value = "journal_search_new", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/newAddition")
    @RequiresAuthentication
    public JsonResult searchNewAddition(@ApiParam("搜寻条件") @RequestBody SearchDto2 searchDto2) {
        validation(searchDto2);
        return journalSearchService.getNewAddition(searchDto2.getPageNum(), searchDto2.getIfDesc(), searchDto2.getKeyWord(), searchDto2.getSubject());
    }

    @ApiOperation(value = "当前")
    @Cacheable(value = "journal_search_current", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/current")
    @RequiresAuthentication
    public JsonResult current(@ApiParam("当前期搜寻") @RequestBody SearchDto2 searchDto2) {
        validation(searchDto2);
        return journalSearchService.getCurrent(searchDto2.getPageNum(), searchDto2.getIfDesc(), searchDto2.getKeyWord(), searchDto2.getSubject());
    }

    @ApiOperation(value = "journal下载", notes = "直接下载")
    @PostMapping(value = "/download")
    @RequiresAuthentication
    public ResponseEntity<byte[]> journalDownload(@ApiParam("下载的具体条目") @RequestBody DownloadDto downloadDto) throws Exception {
        List<Long> journalIdList = downloadDto.getData();
        List<Journal> esiInfos = journalSearchService.getJournalById(journalIdList);
        List<BaseRowModel> esiExcelDownloads = esiInfos.stream().map(JournalDownloadModel::new).collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullList(esiExcelDownloads)) {
            throw new NotFoundException("Journal的id不正确");
        }
        byte[] bytes = ExcelUtil.getDownByte(esiExcelDownloads, 2);
        String fileName = Integer.toString(("JOURNAL " + DATE_FORMAT.format(new Date())).hashCode());
        return DownloadUtil.getResponseEntity(fileName, bytes);
    }


    @ApiOperation(value = "期刊信息条件搜索", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "journal_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/all")
    @RequiresAuthentication
    public JsonResult esiSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        /*
        通过传输来的数据进行判断
        需要判断的数据：page(页码，如果没有默认为0)，keyWord，ifDesc(默认为true)，month，year，subject
        总共16中情况
        */
        if (searchDto == null) {
            throw new NotFoundException("Journal搜寻条件为空!");
        }
        Integer page = searchDto.getPage();
        Boolean ifDesc = searchDto.getIfDesc();
        String keyWord = searchDto.getKeyWord();
        ConditionDto conditionDto = searchDto.getConditionData();
        List<Integer> monthList = conditionDto.getMonth();
        List<Integer> yearList = conditionDto.getYear();
        List<Integer> subject = conditionDto.getSubject();
        if (page == null) page = 0;
        if (ifDesc == null) ifDesc = true;
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            if (!ObjectUtil.ifNotNullList(monthList)) {
                if (!ObjectUtil.ifNotNullList(yearList)) {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByNone(page, ifDesc);
                    } else {
                        return journalSearchService.searchByCategory(page, ifDesc, subject);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByYear(page, ifDesc, yearList);
                    } else {
                        return journalSearchService.searchByCategoryAndYear(page, ifDesc, subject, yearList);
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullList(yearList)) {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByMonth(page, ifDesc, monthList);
                    } else {
                        return journalSearchService.searchByCategoryAndMonth(page, ifDesc, subject, monthList);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByYearAndMonth(page, ifDesc, yearList, monthList);
                    } else {
                        return journalSearchService.searchByCategoryAndYearAndMonth(page, ifDesc, subject, yearList, monthList);
                    }
                }
            }
        } else {
            if (!ObjectUtil.ifNotNullList(monthList)) {
                if (!ObjectUtil.ifNotNullList(yearList)) {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByKeyWord(page, ifDesc, keyWord);
                    } else {
                        return journalSearchService.searchByCategoryAndKeyWord(page, ifDesc, subject, keyWord);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByYearAndKeyWord(page, ifDesc, yearList, keyWord);
                    } else {
                        return journalSearchService.searchByCategoryAndYearAndKeyWord(page, ifDesc, subject, yearList, keyWord);
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullList(yearList)) {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByMonthAndKeyWord(page, ifDesc, monthList, keyWord);
                    } else {
                        return journalSearchService.searchByCategoryAndMonthAndKeyWord(page, ifDesc, subject, monthList, keyWord);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullList(subject)) {
                        return journalSearchService.searchByYearAndMonthAndKeyWord(page, ifDesc, yearList, monthList, keyWord);
                    } else {
                        return journalSearchService.searchByAll(page, ifDesc, subject, yearList, monthList, keyWord);
                    }
                }
            }
        }
    }

    private void validation(SearchDto2 searchDto2) {
        if (searchDto2.getPageNum() == null) {
            throw new NotFoundException("页码不存在");
        }
        if (searchDto2.getIfDesc() == null) {
            throw new NotFoundException("判断条件不存在");
        }
    }
}
