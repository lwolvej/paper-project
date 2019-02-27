package org.duohuo.paper.service.impl;

import org.duohuo.paper.Constants;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.repository.CategoryRepository;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Override
    public JsonResult getAllCategory() {
        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(HttpStatus.OK.name());
        if (redisRepository.has("all_category")) {
            result.setData(redisRepository.get("all_category"));
        } else {
            List<Category> categories = categoryRepository.findAll();
            redisRepository.set("all_category", categories, Constants.CATEGORY_EXPIRE_TIME);
            result.setData(categories);
        }
        return result;
    }
}
