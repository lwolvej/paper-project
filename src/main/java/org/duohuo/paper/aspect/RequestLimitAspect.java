package org.duohuo.paper.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.duohuo.paper.annotation.RequestLimit;
import org.duohuo.paper.exceptions.RequestLimitException;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Timer;
import java.util.TimerTask;

@Aspect
@Component("requestLimitAspect")
public class RequestLimitAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitAspect.class);

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, final RequestLimit limit) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            LOGGER.warn("记录次数, 缺少参数");
        } else {
            HttpServletRequest request = attributes.getRequest();
            String ip = IpUtil.getRemoteIp(request);
            String uri = request.getRequestURI();
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getDeclaringTypeName() + ":" + signature.getName();
            String key = "request_limit".concat(ip).concat(uri).concat(methodName);
            int count = 0;
            if (!redisRepository.has(key)) {
                redisRepository.set(key, ++count);
            } else {
                count = (int) redisRepository.get(key);
                redisRepository.set(key, ++count);
            }
            if (count > 0) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        redisRepository.del(key);
                    }
                }, limit.time());
            }
            if (count > limit.count()) {
                LOGGER.warn("用户:{}, 访问接口:{},超过规定次数:{}次.", ip, uri, count - limit.count());
                throw new RequestLimitException("用户超过规定访问次数");
            }
        }
    }
}
