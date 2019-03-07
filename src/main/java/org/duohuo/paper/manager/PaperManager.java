package org.duohuo.paper.manager;

import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.PaperType;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.repository.PaperRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("paperManager")
public class PaperManager {

    @Resource(name = "paperRepository")
    private PaperRepository paperRepository;

    public List<Long> createNewPaperIdList(final List<Long> paperIdList) {
        List<Long> newPaperIdList = paperIdList.stream()
                .distinct()
                .flatMap(id -> Stream.of(paperRepository.existsById(id) ? id : null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullCollection(newPaperIdList)) {
            throw new NotFoundException("无法通过给定paperId查找到相关数据:" + paperIdList);
        }
        return newPaperIdList;
    }

    public Paper save(final Paper paper) {
        return paperRepository.save(paper);
    }

    public void saveAll(final List<Paper> paperList) {
        paperRepository.saveAll(paperList);
    }

    public Optional<Paper> findByAccessionNumberAndTime(final String accessionNumber, final Time time) {
        return paperRepository.findByAccessionNumberAndTime(accessionNumber, time);
    }

    public void deleteByYearAndMonth(final Time time, final List<PaperType> paperTypeList) {
        paperRepository.deleteAllByTimeAndPaperTypeIn(time, paperTypeList);
    }

    @Cacheable(value = "paper_find_id_list", keyGenerator = "redisKeyGenerator")
    public List<Paper> findAllByIdList(final List<Long> idList) {
        return paperRepository.findAllByPaperIdIn(idList);
    }

    @Cacheable(value = "paper_find_max_time_data", keyGenerator = "redisKeyGenerator")
    public Optional<Paper> findMaxTimeDataByAccessionNumberPaperTypeList(final String accessionNumber, final List<Integer> schoolPaperTypeList) {
        return paperRepository.findMaxTimeDataByAccessionNumberPaperTypeIn(accessionNumber, schoolPaperTypeList);
    }

    @Cacheable(value = "paper_find_page_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByCategoryList(final List<Integer> categoryList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByCategory_CategoryIdInAndPaperTypeIn(categoryList, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByTimeList(final List<Integer> timeList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByTime_TimeIdInAndPaperTypeIn(timeList, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_category_and_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByCategoryAndTimeList(final List<Integer> categoryList, final List<Integer> timeList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(categoryList, timeList, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_none", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByNone(final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByPaperTypeIn(paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_accessionNumber", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByAccessionNumber(final String accessionNumber, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByAccessionNumberAndPaperTypeIn(accessionNumber, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_doi", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByDoi(final String doi, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByDoiAndPaperTypeIn(doi, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_keyWord", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByKeyWord(final String keyWord, final List<Integer> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByKeyWordAndPaperTypeIn(keyWord, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_accessionNumber_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByAccessionNumberAndCategoryList(final String accessionNumber, final List<Integer> categoryList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByCategory_CategoryIdInAndAccessionNumberAndPaperTypeIn(categoryList, accessionNumber, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_doi_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByDoiAndCategoryList(final String doi, final List<Integer> categoryList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByCategory_CategoryIdInAndDoiAndPaperTypeIn(categoryList, doi, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_keyWord_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByKeyWordAndCategoryList(final String keyWord, final List<Integer> categoryList, final List<Integer> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByKeyWordAndCategory_CategoryIdInAndPaperTypeIn(keyWord, categoryList, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_accessionNumber_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByAccessionNumberAndTimeList(final String accessionNumber, final List<Integer> timeList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByTime_TimeIdInAndAccessionNumberAndPaperTypeIn(timeList, accessionNumber, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_doi_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByDoiAndTimeList(final String doi, final List<Integer> timeList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByTime_TimeIdInAndDoiAndPaperTypeIn(timeList, doi, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_keyWord_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByKeyWordAndTimeList(final String keyWord, final List<Integer> timeList, final List<Integer> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByKeyWordAndTime_TimeIdInAndPaperTypeIn(keyWord, timeList, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_accessionNumber_category_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByAccessionNumberAndCategoryListAndTimeList(final String accessionNumber, final List<Integer> categoryList, final List<Integer> timeList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndAccessionNumberAndPaperTypeIn(categoryList, timeList, accessionNumber, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_doi_category_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByDoiAndCategoryListAndTimeList(final String doi, final List<Integer> categoryList, final List<Integer> timeList, final List<PaperType> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndDoiAndPaperTypeIn(categoryList, timeList, doi, paperTypeList, pageRequest);
    }

    @Cacheable(value = "paper_find_page_keyWord_category_time_list", keyGenerator = "redisKeyGenerator")
    public Page<Paper> findAllByKeyWordAndCategoryListAndTimeList(final String keyWord, final List<Integer> categoryList, final List<Integer> timeList, final List<Integer> paperTypeList, final PageRequest pageRequest) {
        return paperRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(keyWord, categoryList, timeList, paperTypeList, pageRequest);
    }
}
