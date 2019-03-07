package org.duohuo.paper.facade;

import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.TimeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("timeFacade")
public class TimeFacade {

    @Resource(name = "timeServiceImpl")
    private TimeService timeService;

    @Cacheable(value = "time_facade_all_year", keyGenerator = "redisKeyGenerator")
    public JsonResult getTimeAllYear() {
        return timeService.getAllYear();
    }
}
