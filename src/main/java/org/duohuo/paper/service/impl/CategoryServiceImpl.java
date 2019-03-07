package org.duohuo.paper.service.impl;

import org.duohuo.paper.manager.CategoryManager;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("categoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Override
    public JsonResult getAllCategory() {
        return new JsonResult(
                HttpStatus.OK.value(), HttpStatus.OK.name(),
                categoryManager.findAll()
        );
    }
}
