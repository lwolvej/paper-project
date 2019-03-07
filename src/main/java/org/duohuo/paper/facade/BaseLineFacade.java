package org.duohuo.paper.facade;

import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.dto.BaseLineSearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.BaseLineExcelService;
import org.duohuo.paper.service.BaseLineSearchService;
import org.duohuo.paper.utils.ObjectUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component("baseLineFacade")
public class BaseLineFacade {

    @Resource(name = "baseLineSearchServiceImpl")
    private BaseLineSearchService baseLineSearchService;

    @Resource(name = "baseLineExcelServiceImpl")
    private BaseLineExcelService baseLineExcelService;

    public JsonResult uploadFacade(final MultipartFile file) {
        if (file == null) {
            throw new NotFoundException("处理文件未上传!");
        }
        String fileName = file.getOriginalFilename();
        if (!RegexUtil.excelFileValidation(fileName)) {
            throw new ExcelException("基准线上传的文件不规范! " + fileName);
        }
        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new ExcelException("基准线上传excel文件出错:" + e.getMessage());
        }
        return baseLineExcelService.insertBaseLineData(data, fileName);
    }

    @Cacheable(value = "base_line_facade_search_category", keyGenerator = "redisKeyGenerator")
    public JsonResult searchByCategoryList(final BaseLineSearchDto searchDto) {
        if (searchDto == null) {
            throw new NotFoundException("基准线查询信息为空");
        }
        List<Integer> categoryList = searchDto.getSubjects();
        if (!ObjectUtil.ifNotNullCollection(categoryList)) {
            throw new NotFoundException("基准线查询学科信息为空");
        }
        return baseLineSearchService.searchByCategory(categoryList);
    }

    @Cacheable(value = "base_line_facade_search_all", keyGenerator = "redisKeyGenerator")
    public JsonResult searchByAll() {
        return baseLineSearchService.searchByAll();
    }
}
