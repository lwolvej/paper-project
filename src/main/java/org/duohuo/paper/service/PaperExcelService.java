package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

public interface PaperExcelService {

    @Transactional
    JsonResult insertPaperExcel(List<String> filePaths, Integer year, Integer month, Integer type, String fileName);
}
