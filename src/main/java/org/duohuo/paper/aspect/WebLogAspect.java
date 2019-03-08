package org.duohuo.paper.aspect;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.duohuo.paper.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component("webLogAspect")
public class WebLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    //登录切面
    @Pointcut("execution(* org.duohuo.paper.service.impl.UserServiceImpl.login(..)))")
    public void loginPointCut() {
    }

    //注册切面
    @Pointcut("execution(* org.duohuo.paper.service.impl.UserServiceImpl.register(..))")
    public void registerPointCut() {
    }

    //查询切面
    @Pointcut("within(org.duohuo.paper.service.impl.*Search*)")
    public void searchPointCut() {
    }

    //删除切面
    @Pointcut("execution(* org.duohuo.paper.service.impl..*.delete*(..))")
    public void deletePointCut() {
    }

    //上传切面
    @Pointcut("execution(* org.duohuo.paper.service.impl..*.insert*(..))")
    public void uploadPointCut() {
    }

    @Before(value = "loginPointCut() && args(userName, password, uuid, code)", argNames = "userName,password,uuid,code")
    public void loginBefore(final String userName, final String password, final String uuid, final String code) {
        LOGGER.info("用户:{}尝试登录", userName);
    }

    @AfterReturning(value = "loginPointCut() && args(userName, password, uuid, code)", argNames = "userName,password,uuid,code")
    public void loginReturning(final String userName, final String password, final String uuid, final String code) {
        LOGGER.info("用户:{}登录成功", userName);
    }

    @Before(value = "registerPointCut() && args(userName, password, uuid, code)", argNames = "userName,password,uuid,code")
    public void registerBefore(final String userName, final String password, final String uuid, final String code) {
        String jwtCode = (String) SecurityUtils.getSubject().getPrincipal();
        if (jwtCode == null) {
            String user = JwtUtil.getUsername(jwtCode);
            if (user == null) {
                LOGGER.warn("无效的token正在注册用户,检查系统!注册名:{}", userName);
            } else {
                LOGGER.info("用户:{}注册账号:{}", user, userName);
            }
        } else {
            LOGGER.warn("注册用户但是查找不到提交者信息,注册名:{}", userName);
        }
    }

    @AfterReturning(value = "registerPointCut() && args(userName, password, uuid, code)", argNames = "userName,password,uuid,code")
    public void registerReturning(final String userName, final String password, final String uuid, final String code) {
        LOGGER.info("成功注册用户:{}", userName);
    }

    @Before("searchPointCut()")
    public void searchBefore(final JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        String jwtCode = (String) SecurityUtils.getSubject().getPrincipal();
        if (jwtCode != null) {
            String user = JwtUtil.getUsername(jwtCode);
            if (user != null) {
                LOGGER.info("用户{}查询信息,方法:{},参数:{}", user, methodName, args);
            } else {
                LOGGER.warn("无效的token,检查系统!,调用查询方法:{},参数:{}", methodName, args);
            }
        } else {
            LOGGER.warn("无法获取用户身份,检查系统!,调用方法:{},参数:{}", methodName, args);
        }
    }

    @AfterReturning("searchPointCut()")
    public void searchReturning(final JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        String jwtCode = (String) SecurityUtils.getSubject().getPrincipal();
        if (jwtCode != null) {
            String user = JwtUtil.getUsername(jwtCode);
            if (user != null) {
                LOGGER.info("用户:{}成功查询到数据,方法:{}", user, methodName);
            } else {
                LOGGER.info("无效的token但是仍然查询到数据,方法:{}", methodName);
            }
        } else {
            LOGGER.warn("找不到code但是仍然查询到数据,检查系统!调用方法:{},参数:{}", methodName, args);
        }
    }

    @Before("deletePointCut()")
    public void deleteBefore(final JoinPoint joinPoint) {
        String args = Arrays.toString(joinPoint.getArgs());
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName();
        String jwtCode = (String) SecurityUtils.getSubject().getPrincipal();
        if (jwtCode != null) {
            String user = JwtUtil.getUsername(jwtCode);
            if (user != null) {
                LOGGER.info("用户:{}尝试删除数据,方法:{},参数", user, methodName, args);
            } else {
                LOGGER.info("无效的token,检查系统!方法:{},参数:{}", methodName, args);
            }
        } else {
            LOGGER.warn("找不到code,检查系统!调用方法:{},参数:{}", methodName, args);
        }
    }

    @AfterReturning("deletePointCut()")
    public void deleteReturning(final JoinPoint joinPoint) {
        String args = Arrays.toString(joinPoint.getArgs());
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName();
        LOGGER.info("成功删除数据,方法:{},参数:{}", methodName, args);
    }

    @Before("uploadPointCut()")
    public void uploadBefore(final JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName();
        LOGGER.info("上传数据,方法:{}", methodName);
    }

    @AfterReturning("uploadPointCut()")
    public void uploadReturning(final JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName();
        LOGGER.info("成功上传数据,方法:{}", methodName);
    }
}
