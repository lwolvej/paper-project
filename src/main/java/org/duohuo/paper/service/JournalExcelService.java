package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

public interface JournalExcelService {

    @Transactional
    JsonResult insertJournalExcel(InputStream stream, Integer year, Integer month, String fileName) throws Exception;
}
