package org.duohuo.paper.manager;

import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.repository.JournalRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.duohuo.paper.utils.TimeUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("journalManager")
public class JournalManager {

    @Resource(name = "journalRepository")
    private JournalRepository journalRepository;

    public void deleteAllByTime(final Integer year, final Integer month) {
        Integer timeId = TimeUtil.createTimeIdByYearAndMonth(year, month);
        journalRepository.deleteAllByTime_TimeId(timeId);
    }

    public void saveAll(final List<Journal> journalList) {
        journalRepository.saveAll(journalList);
    }

    @Cacheable(value = "journal_find_key_category_time_list", keyGenerator = "redisKeyGenerator")
    public List<Journal> findAllByKeyWordAndCategoryListAndTime(final String keyWord, final List<Integer> categoryList, final Time time1, final Time time2) {
        List<Integer> timeList = new ArrayList<>();
        timeList.add(time1.getTimeId());
        timeList.add(time2.getTimeId());
        if (ObjectUtil.ifNotNullCollection(categoryList)) {
            if (ObjectUtil.ifNotNullString(keyWord)) {
                return journalRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(keyWord, categoryList, timeList);
            } else {
                return journalRepository.findAllByCategory_CategoryIdInAndTime_TimeIdIn(categoryList, timeList);
            }
        } else {
            if (ObjectUtil.ifNotNullString(keyWord)) {
                return journalRepository.findAllByKeyWordAndTime_TimeIdIn(keyWord, timeList);
            } else {
                return journalRepository.findAllByTime_TimeIdIn(timeList);
            }
        }
    }

    @Cacheable(value = "journal_find_id_list", keyGenerator = "redisKeyGenerator")
    public List<Journal> findAllByIdList(final List<Long> journalIdList) {
        List<Long> newJournalIdList = journalIdList.stream()
                .distinct().collect(Collectors.toList());
        return journalRepository.findAllByJournalIdIn(newJournalIdList);
    }

    @Cacheable(value = "journal_find_page_id_list", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAllByIdList(final List<Long> journalIdList, final PageRequest request) {
        return journalRepository.findAllByJournalIdIn(journalIdList, request);
    }

    @Cacheable(value = "journal_find_page_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAllByCategoryList(final List<Integer> categoryList, final PageRequest request) {
        return journalRepository.findAllByCategory_CategoryIdIn(categoryList, request);
    }

    @Cacheable(value = "journal_find_page_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAllByTimeList(final List<Integer> timeList, final PageRequest request) {
        return journalRepository.findAllByTime_TimeIdIn(timeList, request);
    }

    @Cacheable(value = "journal_find_page_keyword", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAllByKeyWord(final String keyWord, final PageRequest pageRequest) {
        return journalRepository.findAllByKeyWord(keyWord, pageRequest);
    }

    @Cacheable(value = "journal_find_page_category_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAllByCategoryAndTimeList(final List<Integer> categoryList, final List<Integer> timeList, final PageRequest pageRequest) {
        return journalRepository.findAllByCategory_CategoryIdInAndTime_TimeIdIn(categoryList, timeList, pageRequest);
    }

    @Cacheable(value = "journal_find_page_keyword_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAllByKeyWordAndCategoryList(final String keyWord, final List<Integer> categoryList, final PageRequest request) {
        return journalRepository.findAllByKeyWordAndCategory_CategoryIdIn(keyWord, categoryList, request);
    }

    @Cacheable(value = "journal_find_page_keyword_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAllByKeyWordAndTimeList(final String keyWord, final List<Integer> timeList, final PageRequest request) {
        return journalRepository.findAllByKeyWordAndTime_TimeIdIn(keyWord, timeList, request);
    }

    @Cacheable(value = "journal_find_page_all", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAll(final String keyWord, final List<Integer> categoryList, final List<Integer> timeList, final PageRequest request) {
        return journalRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(keyWord, categoryList, timeList, request);
    }

    @Cacheable(value = "journal_find_page_none", keyGenerator = "redisKeyGenerator")
    public Page<Journal> findAll(final PageRequest request) {
        return journalRepository.findAll(request);
    }

}
