package org.duohuo.paper.service.impl;

import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.manager.BaseLineManager;
import org.duohuo.paper.manager.CategoryManager;
import org.duohuo.paper.manager.ExcelManager;
import org.duohuo.paper.model.BaseLine;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.BaseLineExcelService;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("baseLineExcelServiceImpl")
public class BaseLineExcelServiceImpl implements BaseLineExcelService {

    @Resource(name = "excelManager")
    private ExcelManager excelManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Resource(name = "baseLineManager")
    private BaseLineManager baseLineManager;

    @SuppressWarnings("unchecked")
    @Override
    public JsonResult insertBaseLineData(byte[] data, String fileName) {
        List<Object> originalObjects = excelManager.baseLineExcelRead(data, fileName);
        int yearNum = 0;
        String maxYear = "";
        String minYear = "z";
        int maxYearInt = 0;
        int minYearInt = 0;
        boolean noContainsAllYear = true;
        List<BaseLine> baseLineResults = new ArrayList<>();
        int size = originalObjects.size();
        for (int i = 1; i < size; i++) {
            //年的处理
            if (i == 2) {
                List<Object> yearObjects = (List<Object>) originalObjects.get(i);
                for (Object o : yearObjects) {
                    if (o != null) {
                        String year = ((String) o).trim();
                        if ("RESEARCH FIELDS".equals(year)) continue;
                        if ("ALL YEARS".equals(year)) {
                            noContainsAllYear = false;
                            continue;
                        }
                        if (year.compareTo(maxYear) > 0 && RegexUtil.numberRegex(year)) {
                            maxYear = year;
                        }
                        if (year.compareTo(minYear) < 0 && RegexUtil.numberRegex(year)) {
                            minYear = year;
                        }
                        yearNum++;
                    }
                }
                if (noContainsAllYear) {
                    throw new ExcelException("Excel文件: " + fileName + "处理时出错! 未包含规定年份!");
                }
                maxYearInt = Integer.parseInt(maxYear);
                minYearInt = Integer.parseInt(minYear);
                if (yearNum == maxYearInt - minYearInt) {
                    throw new ExcelException("Excel文件: " + fileName + "处理时出错! 某年份不存在!");
                }
                yearNum++;
            } else if ((i - 3) % 7 == 0 && i != 164) {
                String categoryName = null;
                List<Object> categoryObjects = (List<Object>) originalObjects.get(i);
                for (Object o : categoryObjects) {
                    if (o != null) {
                        categoryName = ((String) o).trim();
                        break;
                    }
                }
                Category category = categoryManager.findByCategoryName(categoryName);
                if (category == null) {
                    throw new ExcelException("Excel文件: " + fileName + "处理时出错! 学科不存在! Category:" + categoryName);
                }
                List<Object> valueObjects;
                for (int j = 0; j < 6; j++) {
                    valueObjects = (List<Object>) originalObjects.get(j + i + 1);
                    boolean indexPercent = true;
                    String baseLinePercent = "";
                    List<Integer> valueList = new ArrayList<>();
                    for (Object o : valueObjects) {
                        if (o != null) {
                            String value = ((String) o).trim();
                            if (indexPercent) {
                                baseLinePercent = value;
                                indexPercent = false;
                            } else if (RegexUtil.numberRegex(value)) {
                                valueList.add(Integer.parseInt(value));
                            }
                        }
                    }
                    if (valueList.size() != (maxYearInt - minYearInt + 2)) {
                        throw new ExcelException("Excel文件: " + fileName + "处理时出错! 缺少数值!");
                    }
                    for (int k = 0; k < 12; k++) {
                        BaseLine baseLine = new BaseLine();
                        if (k == 11) {
                            baseLine.setYear("ALL YEARS");
                        } else {
                            baseLine.setYear(Integer.toString(minYearInt + k));
                        }
                        baseLine.setPercent(baseLinePercent);
                        baseLine.setValue(valueList.get(k));
                        baseLine.setCategory(category);
                        baseLine.setBaseLineId(baseLinePercent + categoryName + baseLine.getYear());
                        baseLineResults.add(baseLine);
                    }
                }
                i += 5;
            }
        }
        baseLineManager.deleteAllByYearLessThan(minYear);
        baseLineManager.saveAll(baseLineResults);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
