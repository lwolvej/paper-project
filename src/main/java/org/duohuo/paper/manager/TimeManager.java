package org.duohuo.paper.manager;

import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.repository.TimeRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.duohuo.paper.utils.TimeUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("timeManager")
public class TimeManager {

    @Resource(name = "timeRepository")
    private TimeRepository timeRepository;

    public void saveAll(final List<Time> timeList) {
        timeRepository.saveAll(timeList);
    }

    public Time save(final Time time) {
        return timeRepository.save(time);
    }

    public Time save(final Integer year, final Integer month) {
        Time time = new Time();
        time.setYear(year);
        time.setMonth(month);
        time.setTimeId(TimeUtil.createTimeIdByYearAndMonth(year, month));
        return this.save(time);
    }

    public Boolean existByYearAndMont(final Integer year, final Integer month) {
        int timeId = TimeUtil.createTimeIdByYearAndMonth(year, month);
        return timeRepository.existsByTimeId(timeId);
    }

    @Cacheable(value = "time_find_all_year", keyGenerator = "redisKeyGenerator")
    public List<Integer> findAllYear() {
        List<Integer> yearList = timeRepository.findAll()
                .stream()
                .flatMap(time -> Stream.of(time.getYear()))
                .distinct()
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullCollection(yearList)) {
            throw new NotFoundException("不存在年序列!?");
        }
        return yearList;
    }

    public Optional<Time> findByYearAndMonth(final Integer year, final Integer month) {
        return timeRepository.findByYearAndMonth(year, month);
    }

    @Cacheable(value = "time_find_now", keyGenerator = "redisKeyGenerator")
    public Time findNow() {
        Optional<Time> time = timeRepository.findMax();
        if (!time.isPresent()) {
            throw new NotFoundException("没有找到最大的时间点");
        }
        return time.get();
    }

    @Cacheable(value = "time_find_second", keyGenerator = "redisKeyGenerator")
    public Time findSecond(final Time max) {
        Optional<Time> time = timeRepository.findSecond(max.getTimeId());
        if (!time.isPresent()) {
            throw new NotFoundException("没有找到第二的时间点:" + max);
        }
        return time.get();
    }

    @Cacheable(value = "time_find_by_year", keyGenerator = "redisKeyGenerator")
    public List<Integer> createTimeIdListByYear(final List<Integer> yearList) {
        List<Integer> timeIdList = yearList.stream()
                .distinct()
                .flatMap(year -> Stream.of(timeRepository.findAllByYear(year).orElse(null)))
                .filter(Objects::nonNull)
                .flatMap(newYearList -> newYearList.stream().map(Time::getTimeId))
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullCollection(timeIdList)) {
            throw new NotFoundException("无法通过年找到时间序号:" + yearList);
        }
        return timeIdList;
    }

    @Cacheable(value = "time_find_by_month", keyGenerator = "redisKeyGenerator")
    public List<Integer> createTimeIdListByMonth(final List<Integer> monthList) {
        List<Integer> timeIdList = monthList.stream()
                .distinct()
                .flatMap(month -> Stream.of(timeRepository.findAllByMonth(month).orElse(null)))
                .filter(Objects::nonNull)
                .flatMap(newMonthList -> newMonthList.stream().map(Time::getTimeId))
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullCollection(timeIdList)) {
            throw new NotFoundException("无法通过月找到时间序号:" + monthList);
        }
        return timeIdList;
    }

    @Cacheable(value = "time_find_by_year_and_month", keyGenerator = "redisKeyGenerator")
    public List<Integer> createTimeIdListByYearAndMonth(final List<Integer> yearList, final List<Integer> monthList) {
        Set<Integer> timeIdList = new HashSet<>();
        for (Integer yearId : yearList) {
            for (Integer month : monthList) {
                Integer tempTimeId = TimeUtil.createTimeIdByYearAndMonth(yearId, month);
                if (timeRepository.existsByTimeId(tempTimeId)) {
                    timeIdList.add(tempTimeId);
                }
            }
        }
        if (!ObjectUtil.ifNotNullCollection(timeIdList)) {
            throw new NotFoundException("无法通过给定年月找到时间序列:" + yearList + "." + monthList);
        }
        return new ArrayList<>(timeIdList);
    }
}
