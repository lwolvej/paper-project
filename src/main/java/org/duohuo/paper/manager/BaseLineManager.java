package org.duohuo.paper.manager;

import org.duohuo.paper.model.BaseLine;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.repository.BaseLineRepository;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("baseLineManager")
public class BaseLineManager {

    @Resource(name = "baseLineRepository")
    private BaseLineRepository baseLineRepository;

    public void saveAll(final List<BaseLine> baseLines) {
        baseLineRepository.saveAll(baseLines);
    }

    public void deleteAllByYearLessThan(final String year) {
        if (baseLineRepository.existsByYearLessThan(year)) {
            baseLineRepository.deleteAllByYearLessThan(year);
        }
    }

    @Cacheable(value = "base_line_find_category_and_percent_and_year", keyGenerator = "redisKeyGenerator")
    public BaseLine findByCategoryAndPercentAndYear(final Category category) {
        return baseLineRepository.findByCategory_CategoryIdAndPercentAndYear(category.getCategoryId(), "1.00%");
    }

    @Cacheable(value = "base_line_find_category_list", keyGenerator = "redisKeyGenerator")
    public List<BaseLine> findAllByCategoryList(final List<Integer> categoryList) {
        return baseLineRepository.findAllByCategory_CategoryIdIn(categoryList);
    }

    @Cacheable(value = "base_line_find_all_year", keyGenerator = "redisKeyGenerator")
    public List<BaseLine> findAllByYear() {
        return baseLineRepository.findAllByYear("ALL YEARS");
    }
}
