package org.duohuo.paper.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.duohuo.paper.annotation.KeyOperation;
import org.duohuo.paper.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>捕获关键性操作(上传，删除)用户信息</p>
 *
 * @author lwolvej
 */
@Aspect
@Component("KeyOperationAspect")
public class KeyOperationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyOperationAspect.class);

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(keyOperation)")
    public void keyInfo(final KeyOperation keyOperation) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            LOGGER.warn("记录操作, 关键参数为空");
        } else {
            HttpServletRequest request = attributes.getRequest();
            String ip = IpUtil.getRemoteIp(request);
            String uri = request.getRequestURI();
            LOGGER.info("用户:{}. 发出:{}操作.", ip.concat(uri), keyOperation.operation().name());
        }
    }
}
