package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaperExcelService {

    @Transactional
    JsonResult insertSchoolPaperExcelData(List<String> filePaths, Integer year, Integer month, String fileName, String typeFilePath, Integer type);

    @Transactional
    JsonResult insertEsiPaperExcelData(List<String> filePaths, Integer year, Integer month, String fileName, String typeFilePath, Integer type);

    @Transactional
    JsonResult deleteByYearAndMonth(Integer year, Integer month, Integer type);
}
