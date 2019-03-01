package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

public interface BaseLineService {

    JsonResult searchByCategory(List<Integer> categoryIdList);

    JsonResult searchByAll();

    @Transactional
    JsonResult insertBaseLineData(byte[] data, String fileName) throws Exception;
}
