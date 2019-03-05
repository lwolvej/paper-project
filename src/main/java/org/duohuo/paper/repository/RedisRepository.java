package org.duohuo.paper.repository;

import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component("redisRepository")
public class RedisRepository {

    @Resource(name = "myRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 设置一个key的过期时间
     *
     * @param key  constants
     * @param time 过期时间
     * @return 是否操作成功
     */
    public Boolean expire(String key, Long time) {
        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 插入一个值
     *
     * @param key   constants
     * @param value 值
     * @param time  时间（秒）
     */
    public void set(String key, Object value, Long time) {
//        if (!redisTemplate.hasKey(constants)) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//        }
    }

    public Boolean has(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除一个key
     *
     * @param key constants
     */
    @SuppressWarnings("ConstantConditions")
    public void del(String key) {
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    public void delByPattern(String pattern) {
        Set<String> sets = redisTemplate.keys(pattern);
        if (sets != null && sets.size() != 0) {
            redisTemplate.delete(sets);
        }
    }

    /**
     * 根据key获取值
     *
     * @param key constants
     * @return value
     */
    public Object get(String key) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(key))
                .orElse(null);
    }
}

