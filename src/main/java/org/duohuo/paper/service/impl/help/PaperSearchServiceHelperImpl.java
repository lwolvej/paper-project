package org.duohuo.paper.service.impl.help;

import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.PaperType;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.PageResult;
import org.duohuo.paper.model.result.PaperResult;
import org.duohuo.paper.repository.PaperRepository;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component("paperSearchServiceHelper")
public class PaperSearchServiceHelperImpl extends AbstractSearchService implements PaperSearchServiceHelper {

    /*
    1:高被引; 2:热点; 3:本校高被引; 4:本校热点
    1:高被引; 2:热点; 3:本校高被引; 4:本校热点
    1:高被引; 2:热点; 3:本校高被引; 4:本校热点
    */

    private static final Logger LOGGER = LoggerFactory.getLogger(PaperSearchServiceHelperImpl.class);


    @Resource(name = "paperRepository")
    private PaperRepository paperRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Override
    public JsonResult searchByCategoryAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndPaperTypeIn(
                newCategoryIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过category:{};查询到paper信息.", newCategoryIdList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, Integer type) {
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndPaperTypeIn(
                timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过year:{};查询到paper信息.", yearList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, Integer type) {
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> papers = paperRepository.findAllByTime_TimeIdInAndPaperTypeIn(
                timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过month:{};查询到paper信息.", monthList);
        return createJsonResult(papers);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, Integer type) {
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> papers = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(
                newCategoryIdList, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过category:{};和year:{};查询到paper信息", newCategoryIdList, yearList);
        return createJsonResult(papers);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> papers = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(
                newCategoryIdList, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过category:{};和month:{};查询到paper信息", newCategoryIdList, monthList);
        return createJsonResult(papers);
    }

    @Override
    public JsonResult searchByYearAndMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndPaperTypeIn(
                timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过year:{};和month:{};查询到paper信息", yearList, monthList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> papers = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(
                newCategoryIdList, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过category:{};和year:{};和month:{};查询到paper信息", newCategoryIdList, yearList, monthList);
        return createJsonResult(papers);
    }

    @Override
    public JsonResult searchByPaperType(Integer pageNum, Boolean ifDesc, Integer type) {
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> papers = paperRepository.findAllByPaperTypeIn(
                paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功查询到paper信息");
        return createJsonResult(papers);
    }

    @Override
    public JsonResult searchByAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, String AccessionNumber, Integer type) {
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByAccessionNumberAndPaperTypeIn(
                AccessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        LOGGER.info("成功通过accessionNumber:{}查询到相关信息", AccessionNumber);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByDoiByPaperType(Integer pageNum, Boolean ifDesc, String doi, Integer type) {
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByDoiAndPaperTypeIn(
                doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId"));
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByArticleNameByPaperType(Integer pageNum, Boolean ifDesc, String articleName, Integer type) {
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndPaperTypeIn(
                articleName, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String accessionNumber, Integer type) {
        List<PaperType> paperTypes = createPaperType(type);
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndAccessionNumberAndPaperTypeIn(
                newCategoryIdList, accessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String doi, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndDoiAndPaperTypeIn(
                newCategoryIdList, doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String articleName, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndCategory_CategoryIdInAndPaperTypeIn(
                articleName, newCategoryIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String accessionNumber, Integer type) {
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndAccessionNumberAndPaperTypeIn(
                timeIdList, accessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String doi, Integer type) {
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndDoiAndPaperTypeIn(
                timeIdList, doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String articleName, Integer type) {
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndTime_TimeIdInAndPaperTypeIn(
                articleName, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String accessionNumber, Integer type) {
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndAccessionNumberAndPaperTypeIn(
                timeIdList, accessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String doi, Integer type) {
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndDoiAndPaperTypeIn(
                timeIdList, doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String articleName, Integer type) {
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndTime_TimeIdInAndPaperTypeIn(
                articleName, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String accessionNumber, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndAccessionNumberAndPaperTypeIn(
                newCategoryIdList, timeIdList, accessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String doi, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndDoiAndPaperTypeIn(
                newCategoryIdList, timeIdList, doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String articleName, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> timeIdList = createTimeIdByYear(yearList);
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(
                articleName, newCategoryIdList, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String accessionNumber, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndAccessionNumberAndPaperTypeIn(
                newCategoryIdList, timeIdList, accessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String doi, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndDoiAndPaperTypeIn(
                newCategoryIdList, timeIdList, doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String articleName, Integer type) {
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> timeIdList = createTimeIdByMonth(monthList);
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(
                articleName, newCategoryIdList, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String accessionNumber, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndAccessionNumberAndPaperTypeIn(
                timeIdList, accessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String doi, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByTime_TimeIdInAndDoiAndPaperTypeIn(
                timeIdList, doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String articleName, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndTime_TimeIdInAndPaperTypeIn(
                articleName, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndAccessionNumberByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String accessionNumber, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndAccessionNumberAndPaperTypeIn(
                newCategoryIdList, timeIdList, accessionNumber, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndDoiByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String doi, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<PaperType> paperTypes = createPaperType(type);
        Page<Paper> page = paperRepository.findAllByCategory_CategoryIdInAndTime_TimeIdInAndDoiAndPaperTypeIn(
                newCategoryIdList, timeIdList, doi, paperTypes, pageRequestCreate(pageNum, ifDesc, "time", "paperId")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonthAndArticleNameByPaperType(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String articleName, Integer type) {
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        List<Integer> newCategoryIdList = createCategoryId(categoryIdList);
        List<Integer> paperTypes = createPaperType2(type);
        Page<Paper> page = paperRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(
                articleName, newCategoryIdList, timeIdList, paperTypes, pageRequestCreate(pageNum, ifDesc, "time_id", "paper_id")
        );
        return createJsonResult(page);
    }

    @Override
    public JsonResult deleteByYearAndMonthAndPaperType(Integer year, Integer month, Integer type) {
        List<PaperType> paperTypeList = createPaperType(type);
        List<Integer> yearList = new ArrayList<>();
        List<Integer> monthList = new ArrayList<>();
        yearList.add(year);
        monthList.add(month);
        List<Integer> timeIdList = createTimeId(yearList, monthList);
        paperRepository.deleteAllByTime_TimeIdInAndPaperTypeIn(timeIdList, paperTypeList);
        redisRepository.delByPattern("paper_*");
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(), true);
    }

    /******/
    private List<Integer> createCategoryId(List<Integer> categoryIdList) {
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category找到对应资源: " + categoryIdList);
        }
        return newCategoryIdList;
    }

    private List<Integer> createTimeIdByYear(List<Integer> yearList) {
        List<Integer> timeIdList = createTimeIdListByYear(yearList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year找到对应资源: " + yearList);
        }
        return timeIdList;
    }

    private List<Integer> createTimeIdByMonth(List<Integer> monthList) {
        List<Integer> timeIdList = createTimeIdListByMonth(monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过month序列找到对应资源: " + monthList);
        }
        return timeIdList;
    }

    private List<Integer> createTimeId(List<Integer> yearList, List<Integer> monthList) {
        List<Integer> timeIdList = createTimeIdListByYearAndMonth(yearList, monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列和month序列找到对应资源. year: " + yearList + ". month: " + monthList);
        }
        return timeIdList;
    }

    /****/


    //1:高被引; 2:热点; 3:本校高被引; 4:本校热点
    private List<PaperType> createPaperType(Integer type) {
        List<PaperType> paperTypes = new ArrayList<>();
        switch (type) {
            case 1: {
                paperTypes.add(PaperType.HC_PAPER);
                paperTypes.add(PaperType.HC_HOT_PAPER);
                paperTypes.add(PaperType.HC_SHC_PAPER);
                paperTypes.add(PaperType.HC_SHOT_PAPER);
                paperTypes.add(PaperType.HC_HOT_SHC_PAPER);
                paperTypes.add(PaperType.HC_HOT_SHOT_PAPER);
                paperTypes.add(PaperType.HC_SHC_SHOT_PAPER);
            }
            break;
            case 2: {
                paperTypes.add(PaperType.HOT_PAPER);
                paperTypes.add(PaperType.HC_HOT_PAPER);
                paperTypes.add(PaperType.HOT_SHC_PAPER);
                paperTypes.add(PaperType.HOT_SHOT_PAPER);
                paperTypes.add(PaperType.HC_HOT_SHC_PAPER);
                paperTypes.add(PaperType.HC_HOT_SHOT_PAPER);
                paperTypes.add(PaperType.HOT_SHC_SHOT_PAPER);
            }
            break;
            case 3: {
                paperTypes.add(PaperType.SHC_PAPER);
                paperTypes.add(PaperType.HC_SHC_PAPER);
                paperTypes.add(PaperType.HOT_SHC_PAPER);
                paperTypes.add(PaperType.SHC_SHOT_PAPER);
                paperTypes.add(PaperType.HC_HOT_SHC_PAPER);
                paperTypes.add(PaperType.HOT_SHC_SHOT_PAPER);
                paperTypes.add(PaperType.HC_SHC_SHOT_PAPER);
            }
            break;
            case 4: {
                paperTypes.add(PaperType.SHOT_PAPER);
                paperTypes.add(PaperType.HC_SHOT_PAPER);
                paperTypes.add(PaperType.HOT_SHOT_PAPER);
                paperTypes.add(PaperType.SHC_SHOT_PAPER);
                paperTypes.add(PaperType.HC_HOT_SHOT_PAPER);
                paperTypes.add(PaperType.HC_SHC_SHOT_PAPER);
                paperTypes.add(PaperType.HOT_SHC_SHOT_PAPER);
            }
            break;
        }
        paperTypes.add(PaperType.ALL_PAPER);
        return paperTypes;
    }

    /*
     * 高被引：0,4,5,6,10,11,12,14
     * 热点：1,4,7,8,10,11,13,14
     * 本校高被引：2,5,7,9,10,12,13,14
     * 本校热点：3,6,8,9,11,12,13,14
     * */
    private List<Integer> createPaperType2(Integer type) {
        List<Integer> paperTypeList = new ArrayList<>();
        switch (type) {
            case 1: {
                paperTypeList.add(0);
                paperTypeList.add(4);
                paperTypeList.add(5);
                paperTypeList.add(6);
                paperTypeList.add(10);
                paperTypeList.add(11);
                paperTypeList.add(12);
                paperTypeList.add(14);
            }
            break;
            case 2: {
                paperTypeList.add(1);
                paperTypeList.add(4);
                paperTypeList.add(7);
                paperTypeList.add(8);
                paperTypeList.add(10);
                paperTypeList.add(11);
                paperTypeList.add(13);
                paperTypeList.add(14);
            }
            break;
            case 3: {
                paperTypeList.add(2);
                paperTypeList.add(5);
                paperTypeList.add(7);
                paperTypeList.add(9);
                paperTypeList.add(10);
                paperTypeList.add(12);
                paperTypeList.add(13);
                paperTypeList.add(14);
            }
            break;
            case 4: {
                paperTypeList.add(3);
                paperTypeList.add(6);
                paperTypeList.add(8);
                paperTypeList.add(9);
                paperTypeList.add(11);
                paperTypeList.add(12);
                paperTypeList.add(13);
                paperTypeList.add(14);
            }
            break;
        }
        return paperTypeList;
    }

    private JsonResult createJsonResult(Page<Paper> page) {
        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(HttpStatus.OK.name());

        PageResult pageResult = new PageResult();
        pageResult.setData(convertPaperToResult(page.getContent()));
        pageResult.setTotalPages(page.getTotalPages());
        pageResult.setTotalElemNums(page.getTotalElements());
        pageResult.setNowPage(page.getNumber());
        pageResult.setElemNums(page.getSize());

        result.setData(pageResult);
        return result;
    }

    //
    private List<PaperResult> convertPaperToResult(List<Paper> paperList) {
        int num = 1;
        List<PaperResult> results = new ArrayList<>();
        for (Paper paper : paperList) {
            PaperResult result = new PaperResult();
            result.setElemNum(num);
            result.setAccessionNumber(paper.getAccessionNumber());
            result.setAddresses(paper.getAddresses());
            result.setYear(paper.getTime().getYear());
            result.setMonth(paper.getTime().getMonth());
            result.setTimesCited(paper.getTimesCited());
            result.setSource(paper.getSource());
            result.setResearchField(paper.getResearchField());
            result.setPublicationDate(paper.getPublicationDate());
            result.setPmid(paper.getPmid());
            result.setPaperId(paper.getPaperId());
            result.setInstitutions(paper.getInstitutions());
            result.setArticleName(paper.getArticleName());
            result.setDoi(paper.getDoi());
            result.setCategoryName(paper.getCategory().getCategoryName());
            result.setCountries(paper.getCountries());
            result.setAuthors(paper.getAuthors());
            results.add(result);
            num++;
        }
        return results;
    }
}
