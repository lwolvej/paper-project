package org.duohuo.paper.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.duohuo.paper.excel.listener.BaseLineExcelListener;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.BaseLine;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.result.BaseLineResult;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.repository.BaseLineRepository;
import org.duohuo.paper.repository.CategoryRepository;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.service.BaseLineService;
import org.duohuo.paper.utils.ObjectUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("baseLineServiceImpl")
public class BaseLineServiceImpl implements BaseLineService {

    @Resource(name = "baseLineRepository")
    private BaseLineRepository baseLineRepository;

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Override
    public JsonResult searchByCategory(List<Integer> categoryIdList) {
        //去重，同时排除不存在的id
        List<Integer> newCategoryIdList = categoryIdList
                .stream()
                .distinct()
                .map(id -> categoryRepository.existsById(id) ? id : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        //此时如果categoryId没有的话，直接结束
        if (!ObjectUtil.ifNotNullList(newCategoryIdList)) {
            throw new NotFoundException("没有找到对应的Category:" + categoryIdList);
        }
        List<BaseLine> baseLineList = baseLineRepository.findAllByCategory_CategoryIdIn(newCategoryIdList);
        return createJsonResult(baseLineList);
    }

    @Override
    public JsonResult searchByAll() {
        List<BaseLine> baseLines = baseLineRepository.findAllByYear("ALL YEARS");
        return createJsonResult(baseLines);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JsonResult insertBaseLineData(InputStream stream, String fileName) throws Exception {
        List<Object> originalObjects;
        try (InputStream inputStream = new BufferedInputStream(stream)) {
            AnalysisEventListener listener = new BaseLineExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read();
            originalObjects = ((BaseLineExcelListener) listener).getVector();
        } catch (Exception e) {
            throw new ExcelException("Excel文件: " + fileName + "处理时出错! " + e.getMessage());
        }
        if (originalObjects == null) {
            throw new ExcelException("Excel文件: " + fileName + "处理时出错! ");
        }
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
                Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);
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
        if (baseLineRepository.existsByYearLessThan(minYear)) {
            baseLineRepository.deleteAllByYearLessThan(minYear);
        }
        baseLineRepository.saveAll(baseLineResults);
        //删除redis中所有基准线的缓存
        redisRepository.delByPattern("base_line_*");
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    private JsonResult createJsonResult(List<BaseLine> baseLineList) {
        List<BaseLineResult> baseLineResults = baseLineList
                .stream()
                .map(BaseLineResult::new)
                .collect(Collectors.toList());
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(), baseLineResults);
    }
}
