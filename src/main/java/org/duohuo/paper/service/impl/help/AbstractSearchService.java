package org.duohuo.paper.service.impl.help;

import org.duohuo.paper.Constants;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.repository.CategoryRepository;
import org.duohuo.paper.repository.TimeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("abstractSearchService")
public abstract class AbstractSearchService {

    @Resource(name = "timeRepository")
    private TimeRepository timeRepository;

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    //这里有关于一页大小的
    protected PageRequest pageRequestCreate(final Integer pageNum, final Boolean ifDesc, final String... sortBy) {
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
        return PageRequest.of(pageNum, Constants.PAGE_SIZE, sort);
    }

    //过滤categoryId的序列
    //去除重复元素，并过滤掉不存在的类别。
    protected List<Integer> filterCategoryIdList(final List<Integer> categoryIdList) {
        return categoryIdList.stream()
                .distinct()
                .flatMap(id -> Stream.of(categoryRepository.existsById(id) ? id : null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected List<Integer> createTimeIdListByYear(final List<Integer> yearList) {
        return yearList.stream()
                .distinct()
                .flatMap(year -> Stream.of(timeRepository.findAllByYear(year).orElse(null)))
                .filter(Objects::nonNull)
                .flatMap(newYearList -> newYearList.stream().map(Time::getTimeId))
                .collect(Collectors.toList());
    }

    //通过月来创建timeId的序列
    //查找的时候排除重复元素，同时过滤掉不存在的元素
    protected List<Integer> createTimeIdListByMonth(final List<Integer> monthList) {
        return monthList.stream()
                .distinct()
                .flatMap(month -> Stream.of(timeRepository.findAllByMonth(month).orElse(null)))
                .filter(Objects::nonNull)
                .flatMap(newMonthList -> newMonthList.stream().map(Time::getTimeId))
                .collect(Collectors.toList());
    }

    protected List<Integer> createTimeIdListByYearAndMonth(final List<Integer> yearList, final List<Integer> monthList) {
        List<Integer> timeIdList = new ArrayList<>();
        for (Integer year : yearList) {
            String yearStr = Integer.toString(year);
            for (Integer month : monthList) {
                String monStr = Integer.toString(month);
                if (month < 10 && month > 1) {
                    monStr = "0" + monStr;
                }
                Integer timeId = (yearStr + monStr).hashCode();
                if (timeRepository.existsByTimeId(timeId)) {
                    timeIdList.add(timeId);
                }
            }
        }
        return timeIdList;
    }

    protected Time findNow() {
        return timeRepository.findMax().orElse(null);
    }

    protected Time findSecond(Time max) {
        return timeRepository.findSecond(max.getTimeId()).orElse(null);
    }
}
