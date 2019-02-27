package org.duohuo.paper.service.impl.help;

import org.duohuo.paper.model.result.JsonResult;

import java.util.List;

public interface PaperSearchServiceHelper {

    JsonResult searchByCategoryAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, Integer type);

    JsonResult searchByYearAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, Integer type);

    JsonResult searchByMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, Integer type);

    JsonResult searchByCategoryAndYearAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, Integer type);

    JsonResult searchByCategoryAndMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, Integer type);

    JsonResult searchByYearAndMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, Integer type);

    JsonResult searchByCategoryAndYearAndMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, Integer type);

    JsonResult searchByPaperType(Integer pageNum, Boolean ifDesc, Integer type);

    JsonResult searchByAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, String AccessionNumber, Integer type);

    JsonResult searchByDoiByPaperType(Integer pageNum, Boolean ifDesc, String doi, Integer type);

    JsonResult searchByArticleNameByPaperType(Integer pageNum, Boolean ifDesc, String articleName, Integer type);

    JsonResult searchByCategoryAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String doi, Integer type);

    JsonResult searchByCategoryAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String articleName, Integer type);

    JsonResult searchByYearAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String accessionNumber, Integer type);

    JsonResult searchByYearAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String doi, Integer type);

    JsonResult searchByYearAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String articleName, Integer type);

    JsonResult searchByMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String articleName, Integer type);

    JsonResult searchByCategoryAndYearAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndYearAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String doi, Integer type);

    JsonResult searchByCategoryAndYearAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String articleName, Integer type);

    JsonResult searchByCategoryAndMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByCategoryAndMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String articleName, Integer type);

    JsonResult searchByYearAndMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByYearAndMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByYearAndMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String articleName, Integer type);

    JsonResult searchByCategoryAndYearAndMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String accessionNumber, Integer type);

    JsonResult searchByCategoryAndYearAndMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String doi, Integer type);

    JsonResult searchByCategoryAndYearAndMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String articleName, Integer type);

    JsonResult deleteByYearAndMonthAndPaperType(Integer year, Integer month, Integer type);
}
