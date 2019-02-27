package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

public interface PaperExcelService {

    @Transactional
    JsonResult insertPaperExcel(InputStream stream, Integer year, Integer month, Integer type, String fileName);
}
