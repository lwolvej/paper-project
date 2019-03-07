package org.duohuo.paper.service.impl;

import org.duohuo.paper.convert.JournalConverter;
import org.duohuo.paper.excel.model.JournalExcelModel;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.manager.CategoryManager;
import org.duohuo.paper.manager.ExcelManager;
import org.duohuo.paper.manager.JournalManager;
import org.duohuo.paper.manager.TimeManager;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.JournalExcelService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("journalExcelServiceImpl")
public class JournalExcelServiceImpl implements JournalExcelService {

    @Resource(name = "excelManager")
    private ExcelManager excelManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Resource(name = "timeManager")
    private TimeManager timeManager;

    @Resource(name = "journalManager")
    private JournalManager journalManager;

    @Override
    public JsonResult deleteByYearAndMonth(final Integer year, final Integer month) {
        if (timeManager.existByYearAndMont(year, month)) {
            journalManager.deleteAllByTime(year, month);
            return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(), true);
        } else {
            throw new NotFoundException("没有发现指定年:" + year + "月:" + month + ".无法删除");
        }
    }

    @Override
    public JsonResult insertJournalExcel(byte[] data, Integer year, Integer month, String fileName) {
        List<JournalExcelModel> excelModelList = excelManager.journalExcelRead(data, fileName);
        Map<String, Category> categoryMap = categoryManager.createCategoryNameMap();
        Time time = timeManager.save(year, month);
        journalManager.deleteAllByTime(year, month);
        List<Journal> journalList = new ArrayList<>();
        for (JournalExcelModel excelModel : excelModelList) {
            String categoryName = excelModel.getCategoryName();
            if (!categoryMap.containsKey(categoryName)) {
                throw new ExcelException("上传文件不规范,出现未知类别:" + categoryName);
            }
            Category category = categoryMap.get(categoryName);
            journalList.add(JournalConverter.convertExcelJournalToEntity(excelModel, category, time));
        }
        journalManager.saveAll(journalList);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
