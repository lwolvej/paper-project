package org.duohuo.paper.service.impl;

import org.duohuo.paper.convert.PaperConverter;
import org.duohuo.paper.manager.*;
import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.PageResult;
import org.duohuo.paper.model.result.PaperResult;
import org.duohuo.paper.service.PaperSearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("paperSearchServiceImpl")
public class PaperSearchServiceImpl implements PaperSearchService {

    @Resource(name = "paperTypeManager")
    private PaperTypeManager paperTypeManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Resource(name = "timeManager")
    private TimeManager timeManager;

    @Resource(name = "pageManager")
    private PageManager pageManager;

    @Resource(name = "paperManager")
    private PaperManager paperManager;

    @Override
    public List<Paper> getPaperListById(List<Long> ids) {
        return paperManager.findAllByIdList(
                paperManager.createNewPaperIdList(ids)
        );
    }

    @Override
    public JsonResult searchByCategory(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final Integer type) {
        return createJsonResult(
                paperManager.findAllByCategoryList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByYear(Integer pageNum, Boolean ifDesc, List<Integer> yearList, Integer type) {
        return createJsonResult(
                paperManager.findAllByTimeList(
                        timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByMonth(Integer pageNum, Boolean ifDesc, List<Integer> monthList, Integer type) {
        return createJsonResult(
                paperManager.findAllByTimeList(
                        timeManager.createTimeIdListByMonth(monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYear(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, Integer type) {
        return createJsonResult(
                paperManager.findAllByCategoryAndTimeList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, Integer type) {
        return createJsonResult(
                paperManager.findAllByCategoryAndTimeList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYear(monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, Integer type) {
        return createJsonResult(
                paperManager.findAllByTimeList(
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, Integer type) {
        return createJsonResult(
                paperManager.findAllByCategoryAndTimeList(
                        categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByNone(Integer pageNum, Boolean ifDesc, Integer type) {
        return createJsonResult(
                paperManager.findAllByNone(
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByAccessionNumber(final Integer pageNum, final Boolean ifDesc, final String AccessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumber(
                        AccessionNumber, paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByDoi(final Integer pageNum, final Boolean ifDesc, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoi(
                        doi, paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByArticleName(final Integer pageNum, final Boolean ifDesc, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWord(
                        articleName, paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndAccessionNumber(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final String accessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumberAndCategoryList(
                        accessionNumber, categoryManager.createNewCategoryIdList(categoryIdList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndDoi(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoiAndCategoryList(
                        doi, categoryManager.createNewCategoryIdList(categoryIdList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndArticleName(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWordAndCategoryList(
                        articleName, categoryManager.createNewCategoryIdList(categoryIdList),
                        paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndAccessionNumber(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final String accessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumberAndTimeList(
                        accessionNumber, timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndDoi(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoiAndTimeList(
                        doi, timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndArticleName(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWordAndTimeList(
                        articleName, timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
                )
        );
    }

    @Override
    public JsonResult searchByMonthAndAccessionNumber(final Integer pageNum, final Boolean ifDesc, final List<Integer> monthList, final String accessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumberAndTimeList(
                        accessionNumber, timeManager.createTimeIdListByMonth(monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByMonthAndDoi(final Integer pageNum, final Boolean ifDesc, final List<Integer> monthList, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoiAndTimeList(
                        doi, timeManager.createTimeIdListByMonth(monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByMonthAndArticleName(final Integer pageNum, final Boolean ifDesc, final List<Integer> monthList, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWordAndTimeList(
                        articleName, timeManager.createTimeIdListByMonth(monthList),
                        paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndAccessionNumber(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final String accessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumberAndCategoryListAndTimeList(
                        accessionNumber, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndDoi(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoiAndCategoryListAndTimeList(
                        doi, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndArticleName(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWordAndCategoryListAndTimeList(
                        articleName, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYear(yearList),
                        paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndAccessionNumber(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> monthList, final String accessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumberAndCategoryListAndTimeList(
                        accessionNumber, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByMonth(monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndDoi(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> monthList, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoiAndCategoryListAndTimeList(
                        doi, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByMonth(monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndArticleName(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> monthList, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWordAndCategoryListAndTimeList(
                        articleName, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByMonth(monthList),
                        paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndMonthAndAccessionNumber(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final List<Integer> monthList, final String accessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumberAndTimeList(
                        accessionNumber, timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndMonthAndDoi(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final List<Integer> monthList, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoiAndTimeList(
                        doi, timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByYearAndMonthAndArticleName(final Integer pageNum, final Boolean ifDesc, final List<Integer> yearList, final List<Integer> monthList, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWordAndTimeList(
                        articleName, timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndAccessionNumber(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final List<Integer> monthList, final String accessionNumber, final Integer type) {
        return createJsonResult(
                paperManager.findAllByAccessionNumberAndCategoryListAndTimeList(
                        accessionNumber, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndDoi(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final List<Integer> monthList, final String doi, final Integer type) {
        return createJsonResult(
                paperManager.findAllByDoiAndCategoryListAndTimeList(
                        doi, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time", "paperId")
                )
        );
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndArticleName(final Integer pageNum, final Boolean ifDesc, final List<Integer> categoryIdList, final List<Integer> yearList, final List<Integer> monthList, final String articleName, final Integer type) {
        return createJsonResult(
                paperManager.findAllByKeyWordAndCategoryListAndTimeList(
                        articleName, categoryManager.createNewCategoryIdList(categoryIdList),
                        timeManager.createTimeIdListByYearAndMonth(yearList, monthList),
                        paperTypeManager.createPaperType2(type),
                        pageManager.pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
                )
        );
    }

    private JsonResult createJsonResult(final Page<Paper> page) {
        PageResult pageResult = new PageResult();
        pageResult.setData(convertPaperToResult(page.getContent(), page.getNumber()));
        pageResult.setTotalPages(page.getTotalPages());
        pageResult.setTotalElemNums(page.getTotalElements());
        pageResult.setNowPage(page.getNumber());
        pageResult.setElemNums(page.getSize());

        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(HttpStatus.OK.name());
        result.setData(pageResult);

        return result;
    }

    private List<PaperResult> convertPaperToResult(final List<Paper> paperList, final Integer pageNow) {
        int num = 10 * pageNow;
        List<PaperResult> results = new ArrayList<>();
        for (Paper paper : paperList) {
            results.add(PaperConverter.convertEntityPaperToResult(paper, ++num));
        }
        return results;
    }
}
