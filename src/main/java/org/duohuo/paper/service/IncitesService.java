package org.duohuo.paper.service;

import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.excel.model.download.IncitesDownloadExcelModel;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IncitesService {

    JsonResult searchByNone(Integer pageNum, Boolean ifDesc);

    JsonResult searchByAccessionNumber(Integer pageNum, Boolean ifDesc, String accessionNumber);

    JsonResult searchByDoi(Integer pageNum, Boolean ifDesc, String doi);

    JsonResult searchByArticleName(Integer pageNum, Boolean ifDesc, String articleName);

    JsonResult searchByCategory(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList);

    JsonResult searchByAccessionNumberAndCategory(Integer pageNum, Boolean ifDesc, String accessionNumber, List<Integer> categoryIdList);

    JsonResult searchByDoiAndCategory(Integer pageNum, Boolean ifDesc, String doi, List<Integer> categoryIdList);

    JsonResult searchByArticleNameAndCategory(Integer pageNum, Boolean ifDesc, String articleName, List<Integer> categoryIdList);

    @Transactional
    JsonResult insertIncitesDate(byte[] data, String fileName);

    List<BaseRowModel> getDownByIdList(List<Integer> idList);
}