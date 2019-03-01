package org.duohuo.paper.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import org.duohuo.paper.excel.listener.JournalExcelListener;
import org.duohuo.paper.excel.model.JournalExcelModel;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.repository.CategoryRepository;
import org.duohuo.paper.repository.JournalRepository;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.repository.TimeRepository;
import org.duohuo.paper.service.JournalExcelService;
import org.duohuo.paper.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("journalExcelServiceImpl")
public class JournalExcelServiceImpl implements JournalExcelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalExcelServiceImpl.class);

    @Resource(name = "journalRepository")
    private JournalRepository journalRepository;

    @Resource(name = "timeRepository")
    private TimeRepository timeRepository;

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Override
    public JsonResult insertJournalExcel(byte[] data, Integer year, Integer month, String fileName) {
        AnalysisEventListener listener;
        //创建InputStream,之后读取excel文件
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            listener = new JournalExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, null, listener);
            reader.read(new Sheet(1, 1, JournalExcelModel.class));
        } catch (Exception e) {
            throw new ExcelException("Excel文件: " + fileName + "处理时出错! " + e.getMessage());
        }
        //获取读取的数据
        List<JournalExcelModel> excelModels = ((JournalExcelListener) listener).getVector();

        //获取所有的category，并以name为key建立hash表
        Map<String, Category> categoryMap = categoryRepository.findAll()
                .stream().collect(Collectors.toMap(Category::getCategoryName, category -> category));
        //利用year和month创建timeId
        Integer timeId = TimeUtil.createTimeIdByYearAndMonth(year, month);
        Time time = new Time();
        time.setYear(year);
        time.setMonth(month);
        time.setTimeId(timeId);
        //如果这个时间不存在，则删除
        if (!timeRepository.existsByTimeId(timeId)) {
            timeRepository.save(time);
        }
        if (journalRepository.existsByTime(time)) {
            journalRepository.deleteAllByTime(time);
        }
        //将ExcelModel转换为Journal
        List<Journal> journals = new ArrayList<>();
        for (JournalExcelModel model : excelModels) {
            String categoryName = model.getCategoryName().trim();
            if (!categoryMap.containsKey(categoryName)) {
                throw new ExcelException("上传Excel文件不规范, 出现未知Subject:" + categoryName);
            }
            Category category = categoryMap.get(categoryName);
            journals.add(model.convertToJournal(category, time));
        }
        //保存
        journalRepository.saveAll(journals);
        redisRepository.delByPattern("journal_*");
        LOGGER.info("成功插入Journal Excel文件:{}", fileName);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
