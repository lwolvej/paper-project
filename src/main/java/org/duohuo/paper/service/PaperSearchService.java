package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaperSearchService extends SearchService {

    JsonResult searchByAccessionNumber(Integer pageNum, Boolean ifDesc, String AccessionNumber);

    JsonResult searchByDoi(Integer pageNum, Boolean ifDesc, String doi);

    JsonResult searchByArticleName(Integer pageNum, Boolean ifDesc, String articleName);

    JsonResult searchByCategoryAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String accessionNumber);

    JsonResult searchByCategoryAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String doi);

    JsonResult searchByCategoryAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String articleName);

    JsonResult searchByYearAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String accessionNumber);

    JsonResult searchByYearAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String doi);

    JsonResult searchByYearAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String articleName);

    JsonResult searchByMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String accessionNumber);

    JsonResult searchByMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String doi);

    JsonResult searchByMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String articleName);

    JsonResult searchByCategoryAndYearAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String accessionNumber);

    JsonResult searchByCategoryAndYearAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String doi);

    JsonResult searchByCategoryAndYearAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String articleName);

    JsonResult searchByCategoryAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String accessionNumber);

    JsonResult searchByCategoryAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String doi);

    JsonResult searchByCategoryAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String articleName);

    JsonResult searchByYearAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String accessionNumber);

    JsonResult searchByYearAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String doi);

    JsonResult searchByYearAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String articleName);

    JsonResult searchByCategoryAndYearAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String accessionNumber);

    JsonResult searchByCategoryAndYearAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String doi);

    JsonResult searchByCategoryAndYearAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String articleName);

    @Transactional
    JsonResult deleteByYearAndMonth(Integer year, Integer month);
}
