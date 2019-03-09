package org.duohuo.paper.service.impl;

import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.convert.IncitesConverter;
import org.duohuo.paper.manager.*;
import org.duohuo.paper.model.*;
import org.duohuo.paper.model.result.IncitesResult;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.PageResult;
import org.duohuo.paper.service.IncitesSearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("incitesSearchServiceImpl")
public class IncitesSearchServiceImpl implements IncitesSearchService {

    @Resource(name = "incitesManager")
    private IncitesManager incitesManager;

    @Resource(name = "paperManager")
    private PaperManager paperManager;

    @Resource(name = "baseLineManager")
    private BaseLineManager baseLineManager;

    @Resource(name = "pageManager")
    private PageManager pageManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Override
    public List<BaseRowModel> getDownByIdList(List<Integer> idList) {
        List<Integer> newIncitesIdList = incitesManager.createNewIncitesIdList(idList);
        List<Incites> incitesList = incitesManager.findAllByIdList(newIncitesIdList);
        List<BaseRowModel> excelModelList = new ArrayList<>();
        for (Incites incites : incitesList) {
            Optional<Paper> hot = paperManager.findMaxTimeSchoolHotDataByAccessionNumber(incites.getAccessionNumber());
            Optional<Paper> highly = paperManager.findMaxTimeSchoolHighDataByAccessionNumber(incites.getAccessionNumber());
            if (hot.isPresent()) {
                if (highly.isPresent()) {
                    excelModelList.add(IncitesConverter.convertIncitesToDownload(incites));
                } else {
                    excelModelList.add(IncitesConverter.convertIncitesToDownload(incites));
                }
            } else {
                if (highly.isPresent()) {
                    excelModelList.add(IncitesConverter.convertIncitesToDownload(incites));
                } else {
                    BaseLine baseLine = baseLineManager.findByCategoryAndPercentAndYear(incites.getCategory());
                    double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                    excelModelList.add(IncitesConverter.convertIncitesToDownload(incites, value));
                }
            }
        }
        return excelModelList;
    }

    @Override
    public JsonResult searchByNone(final Integer pageNum, final Boolean ifDesc) {
        return createJsonResult(
                incitesManager.findAll(
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber"))
        );
    }

    @Override
    public JsonResult searchByAccessionNumber(final Integer pageNum, final Boolean ifDesc, final String accessionNumber) {
        return createJsonResult(
                incitesManager.findAllByAccessionNumber(accessionNumber,
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber"))
        );
    }

    @Override
    public JsonResult searchByDoi(final Integer pageNum, final Boolean ifDesc, final String doi) {
        return createJsonResult(
                incitesManager.findAllByDoi(doi,
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber"))
        );
    }

    @Override
    public JsonResult searchByArticleName(final Integer pageNum, final Boolean ifDesc, final String articleName) {
        return createJsonResult(
                incitesManager.findAllByKeyWord(articleName,
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publication_date", "accession_number"))
        );
    }

    @Override
    public JsonResult searchByCategory(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList) {
        return createJsonResult(
                incitesManager.findAllByCategoryList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber"))
        );
    }

    @Override
    public JsonResult searchByAccessionNumberAndCategory(final Integer pageNum, final Boolean ifDesc, final String accessionNumber, final List<Integer> categoryIdList) {
        return createJsonResult(
                incitesManager.findAllByAccessionNumberCategoryList(
                        accessionNumber, categoryManager.createNewCategoryIdList(categoryIdList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber"))
        );
    }

    @Override
    public JsonResult searchByDoiAndCategory(final Integer pageNum, final Boolean ifDesc, final String doi, final List<Integer> categoryIdList) {
        return createJsonResult(
                incitesManager.findAllByDoiCategoryList(
                        doi, categoryManager.createNewCategoryIdList(categoryIdList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber"))
        );
    }

    @Override
    public JsonResult searchByArticleNameAndCategory(final Integer pageNum, final Boolean ifDesc, final String articleName, final List<Integer> categoryIdList) {
        return createJsonResult(
                incitesManager.findAllByKeyWordCategoryList(
                        articleName, categoryManager.createNewCategoryIdList(categoryIdList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "publication_date", "accession_number")
                )
        );
    }

    private JsonResult createJsonResult(final Page<Incites> page) {
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        int num = 10 * page.getNumber();
        for (Incites incites : pageList) {
            Optional<Paper> hot = paperManager.findMaxTimeSchoolHotDataByAccessionNumber(incites.getAccessionNumber());
            Optional<Paper> highly = paperManager.findMaxTimeSchoolHighDataByAccessionNumber(incites.getAccessionNumber());
            if (hot.isPresent()) {
                if (highly.isPresent()) {
                    resultList.add(IncitesConverter.convertIncitesToResult(incites, 3, ++num));
                } else {
                    resultList.add(IncitesConverter.convertIncitesToResult(incites, 1, ++num));
                }
            } else {
                if (highly.isPresent()) {
                    resultList.add(IncitesConverter.convertIncitesToResult(incites, 2, ++num));
                } else {
                    BaseLine baseLine = baseLineManager.findByCategoryAndPercentAndYear(incites.getCategory());
                    double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                    resultList.add(IncitesConverter.convertIncitesToResult(incites, value, ++num));
                }
            }
        }
        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(HttpStatus.OK.name());

        PageResult pageResult = new PageResult();
        pageResult.setData(resultList);
        pageResult.setTotalPages(page.getTotalPages());
        pageResult.setTotalElemNums(page.getTotalElements());
        pageResult.setNowPage(page.getNumber());
        pageResult.setElemNums(page.getSize());

        result.setData(pageResult);
        return result;
    }
}
