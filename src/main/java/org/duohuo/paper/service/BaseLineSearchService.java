package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;

import java.util.List;

public interface BaseLineSearchService {

    JsonResult searchByCategory(List<Integer> categoryIdList);

    JsonResult searchByAll();
}
