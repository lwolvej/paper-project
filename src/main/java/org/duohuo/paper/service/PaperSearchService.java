package org.duohuo.paper.service;

import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.result.JsonResult;

import java.util.List;

public interface PaperSearchService {

    List<Paper> getPaperListById(List<Long> ids);

    JsonResult searchByCategory(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, Integer type);

    JsonResult searchByYear(Integer pageNum, Boolean ifDesc, List<Integer> yearList, Integer type);

    JsonResult searchByMonth(Integer pageNum, Boolean ifDesc, List<Integer> monthList, Integer type);

    JsonResult searchByCategoryAndYear(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, Integer type);

    JsonResult searchByCategoryAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, Integer type);

    JsonResult searchByYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, Integer type);

    JsonResult searchByCategoryAndYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, Integer type);

    JsonResult searchByNone(Integer pageNum, Boolean ifDesc, Integer type);

    JsonResult searchByAccessionNumber(Integer pageNum, Boolean ifDesc, String AccessionNumber, Integer type);

    JsonResult searchByDoi(Integer pageNum, Boolean ifDesc, String doi, Integer type);

    JsonResult searchByArticleName(Integer pageNum, Boolean ifDesc, String articleName, Integer type);

    JsonResult searchByCategoryAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String doi, Integer type);

    JsonResult searchByCategoryAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String articleName, Integer type);

    JsonResult searchByYearAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String accessionNumber, Integer type);

    JsonResult searchByYearAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String doi, Integer type);

    JsonResult searchByYearAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String articleName, Integer type);

    JsonResult searchByMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String articleName, Integer type);

    JsonResult searchByCategoryAndYearAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndYearAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String doi, Integer type);

    JsonResult searchByCategoryAndYearAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String articleName, Integer type);

    JsonResult searchByCategoryAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByCategoryAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String articleName, Integer type);

    JsonResult searchByYearAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByYearAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByYearAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String articleName, Integer type);

    JsonResult searchByCategoryAndYearAndMonthAndAccessionNumber(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndYearAndMonthAndDoi(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByCategoryAndYearAndMonthAndArticleName(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String articleName, Integer type);
}
