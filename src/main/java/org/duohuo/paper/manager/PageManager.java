package org.duohuo.paper.manager;

import org.duohuo.paper.constants.PageConstant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component("pageManager")
public class PageManager {

    public PageRequest pageRequestCreate(final Integer pageNum, final Boolean ifDesc, final String... sortBy) {
        Sort sort;
        if (ifDesc) {
            if (sortBy.length == 2) {
                sort = Sort.by(Sort.Order.desc(sortBy[0]), Sort.Order.desc(sortBy[1]));
            } else {
                sort = Sort.by(Sort.Order.desc(sortBy[0]));
            }
        } else {
            if (sortBy.length == 2) {
                sort = Sort.by(Sort.Order.asc(sortBy[0]), Sort.Order.asc(sortBy[1]));
            } else {
                sort = Sort.by(Sort.Order.asc(sortBy[0]));
            }
        }
        return PageRequest.of(pageNum, PageConstant.PAGE_SIZE, sort);
    }
}
