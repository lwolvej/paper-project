package org.duohuo.paper.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.duohuo.paper.annotation.KeyOperation;
import org.duohuo.paper.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component("cacheCleanAspect")
public class CacheCleanAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheCleanAspect.class);

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    private void cacheClean() {

    }

    @AfterReturning("cacheClean() && @annotation(keyOperation)")
    private void cacheClean(final KeyOperation keyOperation) {
        switch (keyOperation.type()) {
            case PAPER:
                redisRepository.delByPattern("paper_*");
                LOGGER.info("成功删除论文有关缓存");
                break;
            case INCITES:
                redisRepository.delByPattern("incites_*");
                LOGGER.info("成功删除被引频次有关缓存");
                break;
            case JOURNAL:
                redisRepository.delByPattern("journal_*");
                LOGGER.info("成功删除期刊有关缓存");
                break;
            case BASELINE:
                redisRepository.delByPattern("base_line_*");
                LOGGER.info("成功删除基准线有关缓存");
                break;
        }
        if (keyOperation.operation().equals(KeyOperation.Operation.UPLOAD)) {
            redisRepository.delByPattern("time_*");
            LOGGER.info("成功删除时间有关缓存");
        }
    }
}
