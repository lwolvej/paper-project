package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

public interface IncitesExcelService {

    @Transactional
    JsonResult insertIncitesData(byte[] data, String fileName);
}
