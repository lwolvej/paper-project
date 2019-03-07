package org.duohuo.paper.service.impl;

import org.duohuo.paper.manager.TimeManager;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("timeServiceImpl")
public class TimeServiceImpl implements TimeService {

    @Resource(name = "timeManager")
    private TimeManager timeManager;

    @Override
    public JsonResult getAllYear() {
        return new JsonResult(
                HttpStatus.OK.value(), HttpStatus.OK.name(),
                timeManager.findAllYear()
        );
    }
}
