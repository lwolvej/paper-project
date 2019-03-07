package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

public interface JournalExcelService {

    @Transactional
    JsonResult insertJournalExcel(byte[] data, Integer year, Integer month, String fileName);

    @Transactional
    JsonResult deleteByYearAndMonth(Integer year, Integer month);
}
