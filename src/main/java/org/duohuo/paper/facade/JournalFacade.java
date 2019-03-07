package org.duohuo.paper.facade;

import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.convert.JournalConverter;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.dto.*;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.JournalExcelService;
import org.duohuo.paper.service.JournalSearchService;
import org.duohuo.paper.utils.DownloadUtil;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.ObjectUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("journalFacade")
public class JournalFacade {

    @Resource(name = "journalSearchServiceImpl")
    private JournalSearchService journalSearchService;

    @Resource(name = "journalExcelServiceImpl")
    private JournalExcelService journalExcelService;


    public JsonResult searchFellOutFacade(final SearchDto2 searchDto2) {
        validation(searchDto2);
        return journalSearchService.getFellOut(searchDto2.getPageNum(), searchDto2.getIfDesc(), searchDto2.getKeyWord(), searchDto2.getSubject());
    }

    @Cacheable(value = "journal_facade_search_new_addition", keyGenerator = "redisKeyGenerator")
    public JsonResult searchNewAdditionFacade(final SearchDto2 searchDto2) {
        validation(searchDto2);
        return journalSearchService.getNewAddition(searchDto2.getPageNum(), searchDto2.getIfDesc(), searchDto2.getKeyWord(), searchDto2.getSubject());
    }

    @Cacheable(value = "journal_facade_search_current", keyGenerator = "redisKeyGenerator")
    public JsonResult currentFacade(final SearchDto2 searchDto2) {
        validation(searchDto2);
        return journalSearchService.getCurrent(searchDto2.getPageNum(), searchDto2.getIfDesc(), searchDto2.getKeyWord(), searchDto2.getSubject());
    }

    public JsonResult uploadFacade(final MultipartFile file, final Integer year, final Integer month) {
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
            throw new ExcelException("期刊上传的文件不规范! " + fileName);
        }
        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new ExcelException("期刊上传excel文件出错:" + e.getMessage());
        }
        return journalExcelService.insertJournalExcel(data, year, month, fileName);
    }

    public JsonResult journalDeleteFacade(final DeleteDto deleteDto) {
        if (deleteDto == null) {
            throw new NotFoundException("期刊删除信息不完整");
        }
        if (deleteDto.getMonth() == null || deleteDto.getYear() == null) {
            throw new NotFoundException("期刊删除信息不完整");
        }
        return journalExcelService.deleteByYearAndMonth(deleteDto.getYear(), deleteDto.getMonth());
    }

    public ResponseEntity<byte[]> journalDownloadFacade(final DownloadDto downloadDto) {
        if (downloadDto == null) {
            throw new NotFoundException("期刊的下载条件不存在!");
        }
        List<Long> journalIdList = downloadDto.getData();
        if (!ObjectUtil.ifNotNullCollection(journalIdList)) {
            throw new NotFoundException("期刊的下载序号为空!");
        }
        List<BaseRowModel> esiExcelDownloads = journalSearchService.getJournalById(journalIdList)
                .stream()
                .map(JournalConverter::convertEntityJournalToDownload)
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullList(esiExcelDownloads)) {
            throw new NotFoundException("Journal的id不正确");
        }
        byte[] bytes = ExcelUtil.getDownByte(esiExcelDownloads, 2);
        String fileName = Integer.toString(("JOURNAL " + (new Date())).hashCode());
        return DownloadUtil.getResponseEntity(fileName, bytes);
    }

    @Cacheable(value = "journal_facade_search_all", keyGenerator = "redisKeyGenerator")
    public JsonResult esiSearchFacade(final SearchDto searchDto) {
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
            if (!ObjectUtil.ifNotNullCollection(monthList)) {
                if (!ObjectUtil.ifNotNullCollection(yearList)) {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByNone(page, ifDesc);
                    } else {
                        return journalSearchService.searchByCategory(page, ifDesc, subject);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByYear(page, ifDesc, yearList);
                    } else {
                        return journalSearchService.searchByCategoryAndYear(page, ifDesc, subject, yearList);
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullCollection(yearList)) {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByMonth(page, ifDesc, monthList);
                    } else {
                        return journalSearchService.searchByCategoryAndMonth(page, ifDesc, subject, monthList);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByYearAndMonth(page, ifDesc, yearList, monthList);
                    } else {
                        return journalSearchService.searchByCategoryAndYearAndMonth(page, ifDesc, subject, yearList, monthList);
                    }
                }
            }
        } else {
            if (!ObjectUtil.ifNotNullCollection(monthList)) {
                if (!ObjectUtil.ifNotNullCollection(yearList)) {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByKeyWord(page, ifDesc, keyWord);
                    } else {
                        return journalSearchService.searchByCategoryAndKeyWord(page, ifDesc, subject, keyWord);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByYearAndKeyWord(page, ifDesc, yearList, keyWord);
                    } else {
                        return journalSearchService.searchByCategoryAndYearAndKeyWord(page, ifDesc, subject, yearList, keyWord);
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullCollection(yearList)) {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByMonthAndKeyWord(page, ifDesc, monthList, keyWord);
                    } else {
                        return journalSearchService.searchByCategoryAndMonthAndKeyWord(page, ifDesc, subject, monthList, keyWord);
                    }
                } else {
                    if (!ObjectUtil.ifNotNullCollection(subject)) {
                        return journalSearchService.searchByYearAndMonthAndKeyWord(page, ifDesc, yearList, monthList, keyWord);
                    } else {
                        return journalSearchService.searchByAll(page, ifDesc, subject, yearList, monthList, keyWord);
                    }
                }
            }
        }
    }

    private void validation(SearchDto2 searchDto2) {
        if (searchDto2 == null) {
            throw new NotFoundException("期刊检索条件不存在");
        }
        if (searchDto2.getPageNum() == null) {
            throw new NotFoundException("期刊检索页码不存在");
        }
        if (searchDto2.getIfDesc() == null) {
            throw new NotFoundException("期刊检索判断条件不存在");
        }
    }
}
