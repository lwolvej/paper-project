package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.dto.BaseLineSearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.BaseLineService;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "基准线信息接口", value = "基准线插入，查询")
@RequestMapping("/baseLine")
@RestController
public class BaseLineController {

    @Resource(name = "baseLineServiceImpl")
    private BaseLineService baseLineService;

    @ApiOperation(value = "按学科查询基准线")
    @Cacheable(value = "base_line_search_category", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/byCategory")
    @RequiresAuthentication
    public JsonResult searchBySubjects(@RequestBody BaseLineSearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("基准线查询信息为空!");
        }
        List<Integer> subjects = dto.getSubjects();
        if (!ObjectUtil.ifNotNullList(subjects)) {
            throw new NotFoundException("基准线查询学科信息为空");
        }
        return baseLineService.searchByCategory(subjects);
    }

    @ApiOperation(value = "查询基准线")
    @Cacheable(value = "base_line_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/byAll")
    @RequiresAuthentication
    public JsonResult searchByAll() {
        return baseLineService.searchByAll();
    }
}
