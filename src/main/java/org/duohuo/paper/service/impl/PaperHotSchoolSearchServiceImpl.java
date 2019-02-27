package org.duohuo.paper.service.impl;

import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.PaperSearchService;
import org.duohuo.paper.service.impl.help.PaperSearchServiceHelperImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("paperHotSchoolSearchService")
public class PaperHotSchoolSearchServiceImpl extends PaperSearchServiceHelperImpl implements PaperSearchService {

    @Override
    public JsonResult searchByCategory(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList) {
        return searchByCategoryAndPaperType(pageNum, ifDesc, categoryIdList, 4);
    }

    @Override
    public JsonResult searchByYear(Integer pageNum, Boolean ifDesc, List<Integer> yearList) {
        return searchByYearAndPaperType(pageNum, ifDesc, yearList, 4);
    }

    @Override
    public JsonResult searchByMonth(Integer pageNum, Boolean ifDesc, List<Integer> monthList) {
        return searchByMonthAndPaperType(pageNum, ifDesc, monthList, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYear(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList) {
        return searchByCategoryAndYearAndPaperType(pageNum, ifDesc, categoryIdList, yearList, 4);
    }

    @Override
    public JsonResult searchByCategoryAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList) {
        return searchByCategoryAndMonthAndPaperType(pageNum, ifDesc, categoryIdList, monthList, 4);
    }

    @Override
    public JsonResult searchByYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList) {
        return searchByYearAndMonthAndPaperType(pageNum, ifDesc, yearList, monthList, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList) {
        return searchByCategoryAndYearAndMonthAndPaperType(pageNum, ifDesc, categoryIdList, yearList, monthList, 4);
    }

    @Override
    public JsonResult searchByNone(Integer pageNum, Boolean ifDesc) {
        return searchByPaperType(pageNum, ifDesc, 4);
    }

    @Override
    public JsonResult searchByAccessionNumber(Integer pageNum, Boolean ifDesc, String AccessionNumber) {
        return searchByAccessionNumberByPaperType(pageNum, ifDesc, AccessionNumber, 4);
    }

    @Override
    public JsonResult searchByDoi(Integer pageNum, Boolean ifDesc, String doi) {
        return searchByDoiByPaperType(pageNum, ifDesc, doi, 4);
    }

    @Override
    public JsonResult searchByArticleName(Integer pageNum, Boolean ifDesc, String articleName) {
        return searchByArticleNameByPaperType(pageNum, ifDesc, articleName, 4);
    }

    @Override
    public JsonResult searchByCategoryAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String accessionNumber) {
        return searchByCategoryAndAccessionNumberByPaperType(pageNum, ifDesc, categoryIdList, accessionNumber, 4);
    }

    @Override
    public JsonResult searchByCategoryAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String doi) {
        return searchByCategoryAndDoiByPaperType(pageNum, ifDesc, categoryIdList, doi, 4);
    }

    @Override
    public JsonResult searchByCategoryAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String articleName) {
        return searchByCategoryAndArticleNameByPaperType(pageNum, ifDesc, categoryIdList, articleName, 4);
    }

    @Override
    public JsonResult searchByYearAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String accessionNumber) {
        return searchByYearAndAccessionNumberByPaperType(pageNum, ifDesc, yearList, accessionNumber, 4);
    }

    @Override
    public JsonResult searchByYearAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String doi) {
        return searchByYearAndDoiByPaperType(pageNum, ifDesc, yearList, doi, 4);
    }

    @Override
    public JsonResult searchByYearAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String articleName) {
        return searchByYearAndArticleNameByPaperType(pageNum, ifDesc, yearList, articleName, 4);
    }

    @Override
    public JsonResult searchByMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String accessionNumber) {
        return searchByMonthAndAccessionNumberByPaperType(pageNum, ifDesc, monthList, accessionNumber, 4);
    }

    @Override
    public JsonResult searchByMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String doi) {
        return searchByMonthAndDoiByPaperType(pageNum, ifDesc, monthList, doi, 4);
    }

    @Override
    public JsonResult searchByMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String articleName) {
        return searchByMonthAndArticleNameByPaperType(pageNum, ifDesc, monthList, articleName, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String accessionNumber) {
        return searchByCategoryAndYearAndAccessionNumberByPaperType(pageNum, ifDesc, categoryIdList, yearList, accessionNumber, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String doi) {
        return searchByCategoryAndYearAndDoiByPaperType(pageNum, ifDesc, categoryIdList, yearList, doi, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String articleName) {
        return searchByCategoryAndYearAndArticleNameByPaperType(pageNum, ifDesc, categoryIdList, yearList, articleName, 4);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String accessionNumber) {
        return searchByCategoryAndMonthAndAccessionNumberByPaperType(pageNum, ifDesc, categoryIdList, monthList, accessionNumber, 4);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String doi) {
        return searchByCategoryAndMonthAndDoiByPaperType(pageNum, ifDesc, categoryIdList, monthList, doi, 4);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String articleName) {
        return searchByCategoryAndMonthAndArticleNameByPaperType(pageNum, ifDesc, categoryIdList, monthList, articleName, 4);
    }

    @Override
    public JsonResult searchByYearAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String accessionNumber) {
        return searchByYearAndMonthAndAccessionNumberByPaperType(pageNum, ifDesc, yearList, monthList, accessionNumber, 4);
    }

    @Override
    public JsonResult searchByYearAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String doi) {
        return searchByYearAndMonthAndDoiByPaperType(pageNum, ifDesc, yearList, monthList, doi, 4);
    }

    @Override
    public JsonResult searchByYearAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String articleName) {
        return searchByYearAndMonthAndArticleNameByPaperType(pageNum, ifDesc, yearList, monthList, articleName, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String accessionNumber) {
        return searchByCategoryAndYearAndMonthAndAccessionNumberByPaperType(pageNum, ifDesc, categoryIdList, yearList, monthList, accessionNumber, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String doi) {
        return searchByCategoryAndYearAndMonthAndDoiByPaperType(pageNum, ifDesc, categoryIdList, yearList, monthList, doi, 4);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String articleName) {
        return searchByCategoryAndYearAndMonthAndArticleNameByPaperType(pageNum, ifDesc, categoryIdList, yearList, monthList, articleName, 4);
    }

    @Override
    public JsonResult deleteByYearAndMonth(Integer year, Integer month) {
        return deleteByYearAndMonthAndPaperType(year, month, 4);
    }
}
