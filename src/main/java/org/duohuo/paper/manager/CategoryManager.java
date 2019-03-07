package org.duohuo.paper.manager;

import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.repository.CategoryRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("categoryManager")
public class CategoryManager {

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Cacheable(value = "category_by_name", keyGenerator = "redisKeyGenerator")
    public Category findByCategoryName(final String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        if (!category.isPresent()) {
            throw new NotFoundException("无法通过类别名称查找到类别信息:" + categoryName);
        }
        return category.get();
    }

    @Cacheable(value = "category_all", keyGenerator = "redisKeyGenerator")
    public List<Category> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        if (!ObjectUtil.ifNotNullCollection(categoryList)) {
            throw new NotFoundException("没有找到学科信息!?");
        }
        return categoryList;
    }

    @Cacheable(value = "category_name_map", keyGenerator = "redisKeyGenerator")
    public Map<String, Category> createCategoryNameMap() {
        return categoryRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Category::getCategoryName, category -> category));
    }

    @Cacheable(value = "category_new_id_list", keyGenerator = "redisKeyGenerator")
    public List<Integer> createNewCategoryIdList(final List<Integer> categoryIdList) {
        List<Integer> newCategoryIdList = categoryIdList.stream()
                .distinct()
                .flatMap(id -> Stream.of(categoryRepository.existsById(id) ? id : null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullCollection(newCategoryIdList)) {
            throw new NotFoundException("无法通过给定序列查询到类别信息:" + categoryIdList);
        }
        return newCategoryIdList;
    }
}
