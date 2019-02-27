package org.duohuo.paper.service;

import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JournalSearchService extends SearchService {

    JsonResult getFellOut(Integer pageNum, Boolean ifDesc, String keyWord, List<Integer> categoryIdList);

    JsonResult getNewAddition(Integer pageNum, Boolean ifDesc, String keyWord, List<Integer> categoryIdList);

    JsonResult getCurrent(Integer pageNum, Boolean ifDesc, String keyWord, List<Integer> categoryIdList);

    List<Journal> getJournalById(List<Long> journalIdList);

    JsonResult searchByKeyWord(Integer pageNum, Boolean ifDesc, String keyWord);

    JsonResult searchByCategoryAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String keyWord);

    JsonResult searchByYearAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String keyWord);

    JsonResult searchByMonthAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String keyWord);

    JsonResult searchByCategoryAndYearAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String keyWord);

    JsonResult searchByCategoryAndMonthAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String keyWord);

    JsonResult searchByYearAndMonthAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String keyWord);

    JsonResult searchByAll(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String keyWord);

    @Transactional
    JsonResult deleteByYearAndMonth(Integer year, Integer month);
}
