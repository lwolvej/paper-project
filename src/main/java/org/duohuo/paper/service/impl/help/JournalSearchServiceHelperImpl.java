package org.duohuo.paper.service.impl.help;

import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.model.result.JournalResult;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.PageResult;
import org.duohuo.paper.repository.JournalRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("journalSearchServiceHelper")
public class JournalSearchServiceHelperImpl extends AbstractSearchService implements JournalSearchServiceHelper {

    @Resource(name = "journalRepository")
    private JournalRepository journalRepository;

    @Override
    public List<Long> createJournalByCompareTwoTime(String keyWord, List<Integer> categoryList, Time time1, Time time2) {
        List<Integer> timeIdList = new ArrayList<>();
        timeIdList.add(time1.getTimeId());
        timeIdList.add(time2.getTimeId());
        categoryList = categoryList.stream().distinct().collect(Collectors.toList());
        List<Journal> journals;
        if (ObjectUtil.ifNotNullList(categoryList)) {
            if (ObjectUtil.ifNotNullString(keyWord)) {
                journals = journalRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(keyWord, categoryList, timeIdList);
            } else {
                journals = journalRepository.findAllByCategory_CategoryIdInAndTime_TimeIdIn(categoryList, timeIdList);
            }
        } else {
            if (ObjectUtil.ifNotNullString(keyWord)) {
                journals = journalRepository.findAllByKeyWordAndTime_TimeIdIn(keyWord, timeIdList);
            } else {
                journals = journalRepository.findAllByTime_TimeIdIn(timeIdList);
            }
        }
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
        return resultJournal.stream().flatMap(journal -> Stream.of(journal.getJournalId()))
                .collect(Collectors.toList());
    }

    @Override
    public JsonResult createJsonResult(Page<Journal> page) {
        PageResult pageResult = new PageResult();
        pageResult.setTotalPages(page.getTotalPages());
        pageResult.setTotalElemNums(page.getTotalElements());
        pageResult.setNowPage(page.getNumber());
        pageResult.setElemNums(page.getSize());
        pageResult.setData(convertJournalToResult(page.getContent()));
        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(HttpStatus.OK.name());
        result.setData(pageResult);
        return result;
    }

    @Override
    public List<Journal> createJournalById(List<Long> journalIdList) {
        //先去除重复元素
        List<Long> newJournalIdList = journalIdList.stream()
                .distinct()
                .collect(Collectors.toList());
        return journalRepository.findAllByJournalIdIn(newJournalIdList);
    }

    private List<JournalResult> convertJournalToResult(List<Journal> journalList) {
        int num = 1;
        List<JournalResult> results = new ArrayList<>();
        for (Journal journal : journalList) {
            JournalResult result = new JournalResult();
            result.setElemNum(num);
            result.setYear(journal.getTime().getYear());
            result.setMonth(journal.getTime().getMonth());
            result.setCategoryName(journal.getCategory().getCategoryName());
            result.setEissn(journal.getEissn());
            result.setIssn(journal.getIssn());
            result.setTitle20(journal.getTitle20());
            result.setTitle29(journal.getTitle29());
            result.setJournalId(journal.getJournalId());
            result.setFullTitle(journal.getFullTitle());
            results.add(result);
            num++;
        }
        return results;
    }
}
