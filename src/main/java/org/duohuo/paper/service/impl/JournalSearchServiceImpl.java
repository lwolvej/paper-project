package org.duohuo.paper.service.impl;

import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.repository.JournalRepository;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.repository.TimeRepository;
import org.duohuo.paper.service.JournalSearchService;
import org.duohuo.paper.service.impl.help.JournalSearchServiceHelperImpl;
import org.duohuo.paper.utils.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LwolveJ
 */
@Service("journalSearchService")
public class JournalSearchServiceImpl extends JournalSearchServiceHelperImpl implements JournalSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalSearchServiceImpl.class);

    @Resource(name = "journalRepository")
    private JournalRepository journalRepository;

    @Resource(name = "timeRepository")
    private TimeRepository timeRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Override
    public JsonResult getFellOut(Integer pageNum, Boolean ifDesc, String keyWord, List<Integer> categoryIdList) {
        Time now = findNow();
        if (now == null) {
            throw new NotFoundException("没有找到最新日期");
        }
        Time second = findSecond(now);
        if (second == null) {
            throw new NotFoundException("没有找到上一期日期");
        }
        List<Long> journalIdList = createJournalByCompareTwoTime(keyWord, categoryIdList, now, second);
        Page<Journal> page = journalRepository.findAllByJournalIdIn(journalIdList, pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
        LOGGER.info("成功获取跌出信息");
        return createJsonResult(page);
    }

    @Override
    public JsonResult getNewAddition(Integer pageNum, Boolean ifDesc, String keyWord, List<Integer> categoryIdList) {
        Time now = findNow();
        if (now == null) {
            throw new NotFoundException("没有找到最新的日期");
        }
        Time second = findSecond(now);
        if (second == null) {
            throw new NotFoundException("没有找到上一期的日期");
        }
        List<Long> journalIdList = createJournalByCompareTwoTime(keyWord, categoryIdList, second, now);
        Page<Journal> page = journalRepository.findAllByJournalIdIn(journalIdList, pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
        LOGGER.info("成功获取新增信息");
        return createJsonResult(page);
    }

    @Override
    public JsonResult getCurrent(Integer pageNum, Boolean ifDesc, String keyWord, List<Integer> categoryIdList) {
        Time time = timeRepository.findMax().orElse(null);
        if (time == null) {
            throw new NotFoundException("没有找到最新的信息!");
        }
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
    public JsonResult searchByCategory(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList) {
        //过滤categoryId：去重，并判断所有Id是否存在，不存在则去掉
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        //判断是否为空或null，是则抛出异常结束
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category序列找到对应资源: " + categoryIdList);
        }
        //构造分页，并从数据库中寻找，通过time属性和journalId属性来，这里的sortBy通过类的成员变量的名称来排序
        Page<Journal> page = journalRepository.findAllByCategory_CategoryIdIn(newCategoryIdList,
                pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
        //记录
        LOGGER.info("通过category序列, 查找成功! Category: {}", newCategoryIdList);
        //构造结果
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYear(Integer pageNum, Boolean ifDesc, List<Integer> yearList) {
        //通过年序列创建时间id，同时存在过滤：去重并去除不存在的
        List<Integer> timeIdList = createTimeIdListByYear(yearList);
        //判断time序列是否存在
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列找到对应资源: " + yearList);
        }
        //通过时间序列来查找
        Page<Journal> page = journalRepository.findAllByTime_TimeIdIn(timeIdList,
                pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
        LOGGER.info("通过年序列, 查找成功! Year: {}", yearList);
        //构造结果
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByMonth(Integer pageNum, Boolean ifDesc, List<Integer> monthList) {
        //通过月序列来创建时间id,同时过滤：去重并去除不存在的
        List<Integer> timeIdList = createTimeIdListByMonth(monthList);
        //判断time序列是否存在
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过month序列找到对应资源: " + monthList);
        }
        //通过时间序列来查找
        Page<Journal> page = journalRepository.findAllByTime_TimeIdIn(timeIdList,
                pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
        LOGGER.info("通过月序列, 查找成功! Month: {}", monthList);
        //构造结果
        return createJsonResult(page);
    }

    //@TODO 这里我也不知道为啥这个sort的条件就得这样子。。。
    @Override
    public JsonResult searchByKeyWord(Integer pageNum, Boolean ifDesc, String keyWord) {
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        //通过关键词来检索
        Page<Journal> page = journalRepository.findAllByKeyWord(keyWord,
                pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id"));
        LOGGER.info("通过关键词检索, 查找成功! KeyWord: {}", keyWord);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYear(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList) {
        //过滤categoryId：去重，并判断所有Id是否存在，不存在则去掉
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category序列找到对应资源: " + categoryIdList);
        }
        //通过年序列创建时间id，同时存在过滤：去重并去除不存在的
        List<Integer> timeIdList = createTimeIdListByYear(yearList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列找到对应资源: " + yearList);
        }
        Page<Journal> page = journalRepository.findAllByCategory_CategoryIdInAndTime_TimeIdIn(
                newCategoryIdList, timeIdList, pageRequestCreate(pageNum, ifDesc, "time", "journalId")
        );
        LOGGER.info("通过category和year, 查找成功! Category: {}. Time: {}.", newCategoryIdList, timeIdList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList) {
        //过滤categoryId：去重，并判断所有Id是否存在，不存在则去掉
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category序列找到对应资源: " + categoryIdList);
        }
        //通过月序列来创建时间id,同时过滤：去重并去除不存在的
        List<Integer> timeIdList = createTimeIdListByMonth(monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过month序列找到对应资源: " + monthList);
        }
        Page<Journal> page = journalRepository.findAllByCategory_CategoryIdInAndTime_TimeIdIn(
                newCategoryIdList, timeIdList, pageRequestCreate(pageNum, ifDesc, "time", "journalId")
        );
        LOGGER.info("通过Category和Month, 查找成功! Category: {}. Time: {}.", newCategoryIdList, timeIdList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, String keyWord) {
        //判断关键词是否为空或者不存在!
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        //过滤categoryId：去重，并判断所有Id是否存在，不存在则去掉
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category序列找到对应资源: " + categoryIdList);
        }
        Page<Journal> page = journalRepository.findAllByKeyWordAndCategory_CategoryIdIn(
                keyWord, newCategoryIdList, pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
        );
        LOGGER.info("通过关键词和category查找成功! 关键词: {}. Category: {}", keyWord, newCategoryIdList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList) {
        List<Integer> timeIdList = createTimeIdListByYearAndMonth(yearList, monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列和month序列找到对应资源. year: " + yearList + ". month: " + monthList);
        }
        Page<Journal> page = journalRepository.findAllByTime_TimeIdIn(timeIdList, pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
        LOGGER.info("通过year和month查找成功! Year: {}. Month: {}.", yearList, monthList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> yearList, String keyWord) {
        //判断关键词是否为空或者不存在!
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        List<Integer> timeIdList = createTimeIdListByYear(yearList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列找到对应资源: " + yearList);
        }
        Page<Journal> page = journalRepository.findAllByKeyWordAndTime_TimeIdIn(
                keyWord, timeIdList, pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
        );
        LOGGER.info("通过关键词和Year查找成功! KeyWord:{}. Year:{}", keyWord, yearList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByMonthAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> monthList, String keyWord) {
        //判断关键词是否为空或者不存在!
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        List<Integer> timeIdList = createTimeIdListByMonth(monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过month序列找到对应资源: " + monthList);
        }
        Page<Journal> page = journalRepository.findAllByKeyWordAndTime_TimeIdIn(
                keyWord, timeIdList, pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
        );
        LOGGER.info("通过关键词和month查找成功! KeyWord:{}. Month:{}.", keyWord, monthList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndMonth(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList) {
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category序列找到对应资源: " + categoryIdList);
        }
        List<Integer> timeIdList = createTimeIdListByYearAndMonth(yearList, monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列和month序列找到对应资源. year: " + yearList + ". month: " + monthList);
        }
        Page<Journal> page = journalRepository.findAllByCategory_CategoryIdInAndTime_TimeIdIn(
                newCategoryIdList, timeIdList, pageRequestCreate(pageNum, ifDesc, "time", "journalId")
        );
        LOGGER.info("通过Time和category查找成功. Time:{}. Category:{}", timeIdList, newCategoryIdList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndYearAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, String keyWord) {
        List<Integer> timeIdList = createTimeIdListByYear(yearList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列找到对应资源: " + yearList);
        }
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category序列找到对应资源: " + categoryIdList);
        }
        //判断关键词是否为空或者不存在!
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        Page<Journal> page = journalRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(
                keyWord, newCategoryIdList, timeIdList, pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
        );
        LOGGER.info("通过Year和category和关键词查找成功! Year:{}. category:{}. keyword:{}", yearList, categoryIdList, keyWord);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByCategoryAndMonthAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> monthList, String keyWord) {
        //判断关键词是否为空或者不存在!
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category找到对应资源: " + categoryIdList);
        }
        List<Integer> timeIdList = createTimeIdListByMonth(monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过month序列找到对应资源: " + monthList);
        }
        Page<Journal> page = journalRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(
                keyWord, newCategoryIdList, timeIdList, pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
        );
        LOGGER.info("通过Month和category查找成功! Month:{}. Category:{}", monthList, newCategoryIdList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByYearAndMonthAndKeyWord(Integer pageNum, Boolean ifDesc, List<Integer> yearList, List<Integer> monthList, String keyWord) {
        //判断关键词是否为空或者不存在!
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        List<Integer> timeIdList = createTimeIdListByYearAndMonth(yearList, monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year和month序列找到对应资源. year: " + yearList + ". month: " + monthList);
        }
        Page<Journal> page = journalRepository.findAllByKeyWordAndTime_TimeIdIn(
                keyWord, timeIdList, pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
        );
        LOGGER.info("通过关键词和Time查找成功! 关键词:{}. Year:{}. Month:{}.", keyWord, yearList, monthList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByAll(Integer pageNum, Boolean ifDesc, List<Integer> categoryIdList, List<Integer> yearList, List<Integer> monthList, String keyWord) {
        //判断关键词是否为空或者不存在!
        if (!ObjectUtil.ifNotNullString(keyWord)) {
            throw new NotFoundException("判断关键词为空或不存在!");
        }
        List<Integer> timeIdList = createTimeIdListByYearAndMonth(yearList, monthList);
        if (!ObjectUtil.ifNotNullList(timeIdList)) {
            throw new NotFoundException("无法通过year序列和month序列找到对应资源. year: " + yearList + ". month: " + monthList);
        }
        List<Integer> newCategoryIdList = filterCategoryIdList(categoryIdList);
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("无法通过category序列找到对应资源: " + categoryIdList);
        }
        Page<Journal> page = journalRepository.findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(
                keyWord, newCategoryIdList, timeIdList, pageRequestCreate(pageNum, ifDesc, "time_id", "journal_id")
        );
        LOGGER.info("通过关键词和Time和Category查找成功! 关键词:{}. Year:{}. Month:{}. Category:{}", keyWord, yearList, monthList, newCategoryIdList);
        return createJsonResult(page);
    }

    @Override
    public JsonResult searchByNone(Integer pageNum, Boolean ifDesc) {
        Page<Journal> page = journalRepository.findAll(pageRequestCreate(pageNum, ifDesc, "time", "journalId"));
        return createJsonResult(page);
    }

    @Override
    public List<Journal> getJournalById(List<Long> journalIdList) {
        journalIdList = journalIdList.stream().distinct().collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullList(journalIdList)) {
            throw new NotFoundException("查找的JournalId为空!");
        }
        return journalRepository.findAllByJournalIdIn(journalIdList);
    }

    @Override
    public JsonResult deleteByYearAndMonth(Integer year, Integer month) {
        List<Integer> yearList = new ArrayList<>();
        yearList.add(year);
        List<Integer> monthList = new ArrayList<>();
        monthList.add(month);
        List<Integer> timeIdList = createTimeIdListByYearAndMonth(yearList, monthList);
        if (journalRepository.deleteAllByTime_TimeIdIn(timeIdList)) {
            redisRepository.delByPattern("journal_*");
            return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(), true);
        } else {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), false);
        }
    }
}
