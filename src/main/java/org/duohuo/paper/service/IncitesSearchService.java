package org.duohuo.paper.service;

import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.model.result.JsonResult;

import java.util.List;

public interface IncitesSearchService {

    JsonResult searchByNone(Integer pageNum, Boolean ifDesc);

    JsonResult searchByAccessionNumber(Integer pageNum, Boolean ifDesc, String accessionNumber);

    JsonResult searchByDoi(Integer pageNum, Boolean ifDesc, String doi);

    JsonResult searchByArticleName(Integer pageNum, Boolean ifDesc, String articleName);

    JsonResult searchByCategory(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList);

    JsonResult searchByAccessionNumberAndCategory(Integer pageNum, Boolean ifDesc, String accessionNumber, List<Integer> categoryIdList);

    JsonResult searchByDoiAndCategory(Integer pageNum, Boolean ifDesc, String doi, List<Integer> categoryIdList);

    JsonResult searchByArticleNameAndCategory(Integer pageNum, Boolean ifDesc, String articleName, List<Integer> categoryIdList);

    List<BaseRowModel> getDownByIdList(List<Integer> idList);
}