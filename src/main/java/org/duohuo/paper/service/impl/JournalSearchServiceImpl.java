package org.duohuo.paper.service.impl;

import org.duohuo.paper.convert.JournalConverter;
import org.duohuo.paper.manager.CategoryManager;
import org.duohuo.paper.manager.JournalManager;
import org.duohuo.paper.manager.PageManager;
import org.duohuo.paper.manager.TimeManager;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.model.result.JournalResult;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.PageResult;
import org.duohuo.paper.service.JournalSearchService;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LwolveJ
 */
@Service("journalSearchServiceImpl")
public class JournalSearchServiceImpl implements JournalSearchService {

    @Resource(name = "timeManager")
    private TimeManager timeManager;

    @Resource(name = "journalManager")
    private JournalManager journalManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Resource(name = "pageManager")
    private PageManager pageManager;

    @Override
    public JsonResult getFellOut(final Integer pageNum, final Boolean ifDesc, final String keyWord, final List<Integer> categoryIdList) {
        Time now = timeManager.findNow();
        Time second = timeManager.findSecond(now);
        List<Journal> journals = journalManager.findAllByKeyWordAndCategoryListAndTime(keyWord, categoryIdList, now, second);
        return createJournalIdByCompare(journals, now, pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
    }

    @Override
    public JsonResult getNewAddition(final Integer pageNum, final Boolean ifDesc, final String keyWord, final List<Integer> categoryIdList) {
        Time now = timeManager.findNow();
        Time second = timeManager.findSecond(now);
        List<Journal> journals = journalManager.findAllByKeyWordAndCategoryListAndTime(keyWord, categoryIdList, now, second);
        PageRequest pageRequest = pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId");
        return createJournalIdByCompare(journals, second, pageRequest);
    }

    @Override
    public JsonResult getCurrent(final Integer pageNum, final Boolean ifDesc, final String keyWord, final List<Integer> categoryIdList) {
        Time time = timeManager.findNow();
        List<Integer> yearList = new ArrayList<>();
        List<Integer> monthList = new ArrayList<>();
        yearList.add(time.getYear());
        monthList.add(time.getMonth());
        if (!ObjectUtil.ifNotNullList(categoryIdList)) {
            if (!ObjectUtil.ifNotNullString(keyWord)) {
                return this.searchByYearAndMonth(pageNum, ifDesc, yearList, monthList);
            } else {
                return this.searchByYearAndMonthAndKeyWord(pageNum, ifDesc, yearList, monthList, keyWord);
            }
        } else {
            if (!ObjectUtil.ifNotNullString(keyWord)) {
                return this.searchByCategoryAndYearAndMonth(pageNum, ifDesc, categoryIdList, yearList, monthList);
            } else {
                return this.searchByAll(pageNum, ifDesc, categoryIdList, yearList, monthList, keyWord);
            }
        }
    }

    @Override
    public JsonResult searchByCategory(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList) {
        return createJsonResult(
                journalManager.findAllByCategoryList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId")
                )
        );
    }

    @Override
    public JsonResult searchByYear(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList) {
        return createJsonResult(
                journalManager.findAllByTimeList(
                        timeManager.createTimeIdListByYear(yearList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId")
                )
        );
    }

    @Override
    public JsonResult searchByMonth(final Integer pageNum, final Boolean ifDesc, final List<Integer> monthList) {
        return createJsonResult(
                journalManager.findAllByTimeList(
                        timeManager.createTimeIdListByMonth(monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId")
                )
        );
    }

    @Override
    public JsonResult searchByKeyWord(final Integer pageNum, final Boolean ifDesc, final String keyWord) {
        return createJsonResult(
                journalManager.findAllByKeyWord(
                        keyWord, pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYear(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList) {
        return createJsonResult(
                journalManager.findAllByCategoryAndTimeList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYear(yearList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndMonth(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> monthList) {
        return createJsonResult(
                journalManager.findAllByCategoryAndTimeList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByMonth(monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndKeyWord(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final String keyWord) {
        return createJsonResult(
                journalManager.findAllByKeyWordAndCategoryList(
                        keyWord, categoryManager.createNewCategoryIdList(categoryIdList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndMonth(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final List<Integer> monthList) {
        return createJsonResult(
                journalManager.findAllByTimeList(
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndKeyWord(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final String keyWord) {
        return createJsonResult(
                journalManager.findAllByKeyWordAndTimeList(
                        keyWord, timeManager.createTimeIdListByYear(yearList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByMonthAndKeyWord(final Integer pageNum, final Boolean ifDesc, final List<Integer> monthList, final String keyWord) {
        return createJsonResult(
                journalManager.findAllByKeyWordAndTimeList(
                        keyWord, timeManager.createTimeIdListByMonth(monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonth(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final List<Integer> monthList) {
        return createJsonResult(
                journalManager.findAllByCategoryAndTimeList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndKeyWord(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, String keyWord) {
        return createJsonResult(
                journalManager.findAll(
                        keyWord, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYear(yearList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndKeyWord(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> monthList, final String keyWord) {
        return createJsonResult(
                journalManager.findAll(
                        keyWord, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByMonth(monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndMonthAndKeyWord(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final List<Integer> monthList, final String keyWord) {
        return createJsonResult(
                journalManager.findAllByKeyWordAndTimeList(
                        keyWord, timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByAll(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final List<Integer> monthList, final String keyWord) {
        return createJsonResult(
                journalManager.findAll(
                        keyWord, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
                )
        );
    }

    @Override
    public JsonResult searchByNone(final Integer pageNum, final Boolean ifDesc) {
        return createJsonResult(
                journalManager.findAll(pageManager.pageRequestCreate(pageNum, ifDesc, "time", "journalId"))
        );
    }

    @Override
    public List<Journal> getJournalById(final List<Long> journalIdList) {
        return journalManager.findAllByIdList(journalIdList);
    }

    private JsonResult createJournalIdByCompare(final List<Journal> journals, final Time time1, final PageRequest request) {
        Set<String> fullTitles = new HashSet<>();
        List<Journal> time2JournalList = new ArrayList<>();
        for (Journal journal : journals) {
            if (journal.getTime().equals(time1)) {
                fullTitles.add(journal.getFullTitle());
            } else {
                time2JournalList.add(journal);
            }
        }
        List<Journal> resultJournal = new ArrayList<>();
        for (Journal journal : time2JournalList) {
            if (!fullTitles.contains(journal.getFullTitle())) {
                resultJournal.add(journal);
            }
        }
        List<Long> journalIdList = resultJournal.stream().flatMap(journal -> Stream.of(journal.getJournalId()))
                .collect(Collectors.toList());
        return createJsonResult(
                journalManager.findAllByIdList(
                        journalIdList, request
                )
        );
    }

    private JsonResult createJsonResult(final Page<Journal> page) {
        JsonResult result = new JsonResult();
        result.setMsg(HttpStatus.OK.name());
        result.setCode(HttpStatus.OK.value());
        PageResult pageResult = new PageResult();
        pageResult.setNowPage(page.getNumber());
        pageResult.setElemNums(page.getSize());
        pageResult.setTotalPages(page.getTotalPages());
        pageResult.setTotalElemNums(page.getTotalElements());
        pageResult.setData(convertJournalToResult(page.getContent(), page.getNumber()));
        result.setData(pageResult);
        return result;
    }

    private List<JournalResult> convertJournalToResult(final List<Journal> journalList, final Integer nowPage) {
        int num = nowPage * 10;
        List<JournalResult> results = new ArrayList<>();
        for (Journal journal : journalList) {
            results.add(JournalConverter.convertEntityJournalToResult(journal, ++num));
        }
        return results;
    }
}
