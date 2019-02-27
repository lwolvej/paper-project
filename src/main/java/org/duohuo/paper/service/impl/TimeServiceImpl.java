package org.duohuo.paper.service.impl;

import org.duohuo.paper.Constants;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.repository.TimeRepository;
import org.duohuo.paper.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("timeServiceImpl")
public class TimeServiceImpl implements TimeService {

    @Resource(name = "timeRepository")
    private TimeRepository timeRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Override
    public JsonResult getAllYear() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(HttpStatus.OK.value());
        jsonResult.setMsg(HttpStatus.OK.name());
        if (redisRepository.has("all_year")) {
            jsonResult.setData(redisRepository.get("all_year"));
        } else {
            List<Integer> yearList = timeRepository.findAll()
                    .stream()
                    .flatMap(time -> Stream.of(time.getYear()))
                    .distinct()
                    .collect(Collectors.toList());
            redisRepository.set("all_year", yearList, Constants.TIME_YEAR_EXPIRE_TIME);
            jsonResult.setData(yearList);
        }
        return jsonResult;
    }
}
