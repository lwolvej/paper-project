package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.duohuo.paper.facade.TimeFacade;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "时间接口", value = "时间信息")
@RestController
@RequestMapping("/time")
public class TimeController {

    @Resource(name = "timeFacade")
    private TimeFacade timeFacade;

    @ApiOperation(value = "获取所有年份")
    @GetMapping("/allYear")
    @RequiresAuthentication
    public JsonResult timeAllYear() {
        return timeFacade.getTimeAllYear();
    }
}
