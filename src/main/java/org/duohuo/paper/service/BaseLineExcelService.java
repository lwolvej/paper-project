package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

public interface BaseLineExcelService {

    @Transactional
    JsonResult insertBaseLineData(byte[] data, String fileName);
}
