package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;

import java.util.List;

public interface SearchService {

    JsonResult searchByCategory(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList);

    JsonResult searchByYear(Integer pageNum, Boolean ifDesc, List<Integer> yearList);

    JsonResult searchByMonth(Integer pageNum, Boolean ifDesc, List<Integer> monthList);

    JsonResult searchByCategoryAndYear(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList);

    JsonResult searchByCategoryAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList);

    JsonResult searchByYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList);

    JsonResult searchByCategoryAndYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList);

    JsonResult searchByNone(Integer pageNum, Boolean ifDesc);
}
