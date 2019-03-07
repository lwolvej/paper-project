package org.duohuo.paper.facade;

import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.constants.SearchTypeConstant;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.dto.DownloadDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.IncitesExcelService;
import org.duohuo.paper.service.IncitesSearchService;
import org.duohuo.paper.utils.DownloadUtil;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.ObjectUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("incitesFacade")
public class IncitesFacade {

    @Resource(name = "incitesSearchServiceImpl")
    private IncitesSearchService incitesSearchService;

    @Resource(name = "incitesExcelServiceImpl")
    private IncitesExcelService incitesExcelService;

    public ResponseEntity<byte[]> downloadFacade(final DownloadDto downloadDto) {
        List<Integer> incitesId = downloadDto.getData().stream().map(Long::intValue).collect(Collectors.toList());
        List<BaseRowModel> downloadExcelModels = incitesSearchService.getDownByIdList(incitesId);
        if (!ObjectUtil.ifNotNullList(downloadExcelModels)) {
            throw new NotFoundException("incites的id不正确");
        }
        byte[] bytes = ExcelUtil.getDownByte(downloadExcelModels, 3);
        String fileName = Integer.toString(("INCITES " + (new Date())).hashCode());
        return DownloadUtil.getResponseEntity(fileName, bytes);
    }

    public JsonResult uploadFacade(final MultipartFile file) {
        if (file == null) {
            throw new NotFoundException("被引频次没有上传所需文件");
        }
        String fileName = file.getOriginalFilename();
        if (!RegexUtil.excelFileValidation(fileName)) {
            throw new ExcelException("被引频次没有上传指定excel文件:" + fileName);
        }
        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new ExcelException("被引频次上传excel文件时出错:" + e.getMessage());
        }
        return incitesExcelService.insertIncitesData(data, fileName);
    }

    @Cacheable(value = "incites_facade_search_all", keyGenerator = "redisKeyGenerator")
    public JsonResult searchFacade(final SearchDto searchDto) {
        if (searchDto == null) {
            throw new NotFoundException("被引频次检索信息为空!");
        }
        Integer page = searchDto.getPage();
        Boolean ifDesc = searchDto.getIfDesc();
        if (page == null) page = 1;
        if (ifDesc == null) ifDesc = false;
        String keyWord = searchDto.getKeyWord();
        String keyWordType = searchDto.getKeyWordType();
        List<Integer> subjectList = searchDto.getConditionData().getSubject();
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            if (!ObjectUtil.ifNotNullList(subjectList)) {
                return incitesSearchService.searchByNone(page, ifDesc);
            } else {
                return incitesSearchService.searchByCategory(page, ifDesc, subjectList);
            }
        } else {
            if (!ObjectUtil.ifNotNullList(subjectList)) {
                switch (keyWordType) {
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                        return incitesSearchService.searchByAccessionNumber(page, ifDesc, keyWord);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                        return incitesSearchService.searchByArticleName(page, ifDesc, keyWord);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                        return incitesSearchService.searchByDoi(page, ifDesc, keyWord);
                    default:
                        return new JsonResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
                }
            } else {
                switch (keyWordType) {
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                        return incitesSearchService.searchByAccessionNumberAndCategory(page, ifDesc, keyWord, subjectList);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                        return incitesSearchService.searchByArticleNameAndCategory(page, ifDesc, keyWord, subjectList);
                    case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                        return incitesSearchService.searchByDoiAndCategory(page, ifDesc, keyWord, subjectList);
                    default:
                        return new JsonResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
                }
            }
        }
    }
}
