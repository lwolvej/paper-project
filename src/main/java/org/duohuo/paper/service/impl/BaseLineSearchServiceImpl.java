package org.duohuo.paper.service.impl;

import org.duohuo.paper.convert.BaseLineConverter;
import org.duohuo.paper.manager.BaseLineManager;
import org.duohuo.paper.manager.CategoryManager;
import org.duohuo.paper.model.BaseLine;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.BaseLineSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("baseLineSearchServiceImpl")
public class BaseLineSearchServiceImpl implements BaseLineSearchService {

    @Resource(name = "baseLineManager")
    private BaseLineManager baseLineManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Override
    public JsonResult searchByCategory(List<Integer> categoryIdList) {
        return createJsonResult(
                baseLineManager.findAllByCategoryList(
                        categoryManager.createNewCategoryIdList(categoryIdList)
                )
        );
    }

    @Override
    public JsonResult searchByAll() {
        return createJsonResult(baseLineManager.findAllByYear());
    }

    private JsonResult createJsonResult(List<BaseLine> baseLineList) {
        return new JsonResult(
                HttpStatus.OK.value(), HttpStatus.OK.name(),
                baseLineList.stream()
                        .map(BaseLineConverter::convertBaseLineToResult)
                        .collect(Collectors.toList())
        );
    }
}
