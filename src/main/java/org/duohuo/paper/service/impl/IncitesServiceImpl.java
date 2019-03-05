package org.duohuo.paper.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import org.duohuo.paper.excel.listener.IncitesExcelListener;
import org.duohuo.paper.excel.model.IncitesExcelModel;
import org.duohuo.paper.excel.model.download.IncitesDownloadExcelModel;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.*;
import org.duohuo.paper.model.convert.Converter;
import org.duohuo.paper.model.result.IncitesResult;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.PageResult;
import org.duohuo.paper.repository.BaseLineRepository;
import org.duohuo.paper.repository.CategoryRepository;
import org.duohuo.paper.repository.IncitesRepository;
import org.duohuo.paper.repository.PaperRepository;
import org.duohuo.paper.service.IncitesService;
import org.duohuo.paper.service.impl.help.AbstractSearchService;
import org.duohuo.paper.utils.ConvertUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("incitesServiceImpl")
public class IncitesServiceImpl extends AbstractSearchService implements IncitesService {

    @Resource(name = "incitesRepository")
    private IncitesRepository incitesRepository;

    @Resource(name = "paperRepository")
    private PaperRepository paperRepository;

    @Resource(name = "baseLineRepository")
    private BaseLineRepository baseLineRepository;

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Override
    public List<BaseRowModel> getDownByIdList(List<Integer> idList) {
        List<Incites> incitesList = incitesRepository.findAllByIncitesIdIn(idList);
        List<BaseRowModel> excelModelList = new ArrayList<>();
        for (Incites incites : incitesList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                excelModelList.add(ConvertUtil.convertIncitesToDownload(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue()) / 1.0;
                excelModelList.add(ConvertUtil.convertIncitesToDownload(incites, value));
            }
        }
        return excelModelList;
    }

    @Override
    public JsonResult searchByNone(Integer pageNum, Boolean ifDesc) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber");
        Page<Incites> page = incitesRepository.findAll(pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public JsonResult searchByAccessionNumber(Integer pageNum, Boolean ifDesc, String accessionNumber) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber");
        Page<Incites> page = incitesRepository.findAllByAccessionNumber(accessionNumber, pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(accessionNumber, getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public JsonResult searchByDoi(Integer pageNum, Boolean ifDesc, String doi) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber");
        Page<Incites> page = incitesRepository.findAllByDoi(doi, pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public JsonResult searchByArticleName(Integer pageNum, Boolean ifDesc, String articleName) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publication_date", "accession_number");
        Page<Incites> page = incitesRepository.findAllByKeyWord(articleName, pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public JsonResult searchByCategory(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber");
        Page<Incites> page = incitesRepository.findAllByCategory_CategoryIdIn(categoryIdList, pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public JsonResult searchByAccessionNumberAndCategory(Integer pageNum, Boolean ifDesc, String accessionNumber, List<Integer> categoryIdList) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber");
        Page<Incites> page = incitesRepository.findAllByAccessionNumberAndCategory_CategoryIdIn(accessionNumber, categoryIdList, pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public JsonResult searchByDoiAndCategory(Integer pageNum, Boolean ifDesc, String doi, List<Integer> categoryIdList) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publicationDate", "accessionNumber");
        Page<Incites> page = incitesRepository.findAllByDoiAndCategory_CategoryIdIn(doi, categoryIdList, pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public JsonResult searchByArticleNameAndCategory(Integer pageNum, Boolean ifDesc, String articleName, List<Integer> categoryIdList) {
        Pageable pageable = pageRequestCreate(pageNum, ifDesc, "publication_date", "accession_number");
        Page<Incites> page = incitesRepository.findAllByKeyWordAndCategory_CategoryIdIn(articleName, categoryIdList, pageable);
        List<Incites> pageList = page.getContent();
        List<IncitesResult> resultList = new ArrayList<>();
        for (Incites incites : pageList) {
            Optional<Paper> paper = paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(incites.getAccessionNumber(), getSchoolPaperType());
            if (paper.isPresent()) {
                resultList.add(ConvertUtil.convertIncitesToResult(incites));
            } else {
                BaseLine baseLine = baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(incites.getCategory().getCategoryId(), "1.00%");
                double value = (incites.getCitedTimes() * 1.0) / (baseLine.getValue() * 1.0);
                resultList.add(ConvertUtil.convertIncitesToResult(incites, value));
            }
        }
        return createJsonResult(resultList, page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    //这里就简单的将数据写到数据库
    @Override
    public JsonResult insertIncitesDate(byte[] data, String fileName) {
        AnalysisEventListener listener;
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            listener = new IncitesExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, null, listener);
            reader.read(new Sheet(1, 1, IncitesExcelModel.class));
        } catch (IOException e) {
            throw new ExcelException(e.getMessage());
        }
        List<IncitesExcelModel> incitesExcelModelList = ((IncitesExcelListener) listener).getVector();
        Map<String, Category> categoryMap = categoryRepository.findAll()
                .stream().collect(Collectors.toMap(Category::getCategoryName, category -> category));
        List<Incites> incitesList = new ArrayList<>();
        for (IncitesExcelModel excelModel : incitesExcelModelList) {
            String categoryName = excelModel.getResearchField().trim();
            if (!categoryMap.containsKey(categoryName)) {
                throw new NotFoundException("没有找到指定的学科类别:" + categoryName);
            }
            Category category = categoryMap.get(categoryName);
            Incites incites = excelModel.convertToIncites(category);
            //如果有就设置id，因为jpa是根据id来更新
            incitesRepository.findByAccessionNumber(incites.getAccessionNumber())
                    .ifPresent(incites1 -> incites.setIncitesId(incites1.getIncitesId()));
            incitesList.add(incites);
        }
        incitesRepository.saveAll(incitesList);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    private List<Integer> getSchoolPaperType() {
        List<Integer> paperTypes = new ArrayList<>();
        paperTypes.add(2);
        paperTypes.add(3);
        paperTypes.add(5);
        paperTypes.add(6);
        paperTypes.add(7);
        paperTypes.add(8);
        paperTypes.add(9);
        paperTypes.add(10);
        paperTypes.add(11);
        paperTypes.add(12);
        paperTypes.add(13);
        return paperTypes;
    }

    private JsonResult createJsonResult(final List<IncitesResult> incitesResults, final Integer totalPages,
                                        final Long totalElements, final Integer nowPage, final Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(HttpStatus.OK.name());

        PageResult pageResult = new PageResult();
        pageResult.setData(incitesResults);
        pageResult.setTotalPages(totalPages);
        pageResult.setTotalElemNums(totalElements);
        pageResult.setNowPage(nowPage);
        pageResult.setElemNums(pageSize);

        result.setData(pageResult);
        return result;
    }
}
