package org.duohuo.paper.service.impl;

import org.duohuo.paper.convert.IncitesConverter;
import org.duohuo.paper.excel.model.IncitesExcelModel;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.manager.CategoryManager;
import org.duohuo.paper.manager.ExcelManager;
import org.duohuo.paper.manager.IncitesManager;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.Incites;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.IncitesExcelService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("incitesExcelServiceImpl")
public class IncitesExcelServiceImpl implements IncitesExcelService {

    @Resource(name = "excelManager")
    private ExcelManager excelManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Resource(name = "incitesManager")
    private IncitesManager incitesManager;

    @Override
    public JsonResult insertIncitesData(final byte[] data, final String fileName) {
        List<IncitesExcelModel> excelModels = excelManager.incitesExcelRead(data, fileName);
        Map<String, Category> categoryMap = categoryManager.createCategoryNameMap();
        List<Incites> incitesList = new ArrayList<>();
        for (IncitesExcelModel excelModel : excelModels) {
            String categoryName = excelModel.getResearchField().toUpperCase();
            if (!categoryMap.containsKey(categoryName)) {
                throw new NotFoundException("Incites插入没有找到指定的学科类别:" + categoryName);
            }
            Category category = categoryMap.get(categoryName);
            Incites incites = IncitesConverter.convertExcelIncitesToEntity(excelModel, category);
            //如果有就设置id，因为jpa是根据id来更新
            incitesManager.findByAccessionNumber(incites.getAccessionNumber())
                    .ifPresent(incites1 -> incites.setIncitesId(incites1.getIncitesId()));
            incitesList.add(incites);
        }
        incitesManager.saveAll(incitesList);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
