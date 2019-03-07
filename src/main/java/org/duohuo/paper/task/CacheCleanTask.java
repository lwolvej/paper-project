package org.duohuo.paper.task;

import org.duohuo.paper.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>定时清理redis中缓存</p>
 *
 * @author lwolvej
 */
@Component("cacheCleanTask")
public class CacheCleanTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheCleanTask.class);

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;


    //每三天凌晨两点清空，paper,incites,journal,baseLine
    @Scheduled(cron = "0 0 2 1/10 * ? *")
    public void cacheClean() {
        redisRepository.delByPattern("paper_*");
        LOGGER.info("成功清理关于论文有关缓存");
        redisRepository.delByPattern("incites_*");
        LOGGER.info("成功清理关于被引频次有关缓存");
        redisRepository.delByPattern("journal_*");
        LOGGER.info("成功清理关于期刊有关缓存");
        redisRepository.delByPattern("base_line_*");
        LOGGER.info("成功清理管理基准线有关缓存");
    }

    //每天四点随机清空一个key
    @Scheduled(cron = "0 0 4 * * ? *")
    public void randomClean() {
        redisRepository.delByRandom();
        LOGGER.info("成功随机清理一个缓存");
    }
}
