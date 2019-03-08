package org.duohuo.paper.exceptions;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.duohuo.paper.model.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;

/**
 * @author lwolvej
 */
@RestControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    //出现超过访问次数限制的，表示收到了请求但是禁止
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(RequestLimitException.class)
    public JsonResult handleRequestLimit() {
        return new JsonResult(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name());
    }

    //出现用户信息不符合规范的，表示没有权限
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserException.class)
    public JsonResult handle401(UserException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    //excel相关的信息错误，是用户文件错误，划分为请求出现错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExcelException.class)
    public JsonResult handleExcelException(ExcelException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.BAD_REQUEST.value(), "检查excel格式是否错误,或联系开发人员!");
    }

    //没有找到相关资源，划分为请求出现错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public JsonResult handleNotFoundException(NotFoundException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public JsonResult handleAuthentication(AuthenticationException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name());
    }

    //ShiroException，用户身份验证出现错误
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public JsonResult handle401(ShiroException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), "身份验证未通过!");
    }

    //AuthorizationException，用户的身份验证出现错误
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public JsonResult handle401(AuthorizationException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), "授权不通过!");
    }

    // UnauthorizedException，用户的身份验证出现错误
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public JsonResult handle401(UnauthorizedException ex) {
        LOGGER.warn(ex.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), "登录不合法");
    }

    //CaptchaException，验证码获取出错，服务器内部错误
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CaptchaException.class)
    public JsonResult handleCaptchaException(CaptchaException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "验证码异常!");
    }

    //XssException，错误的请求
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(XssException.class)
    public JsonResult handleXssException(XssException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.BAD_REQUEST.value(), "检测到跨站脚本攻击!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServletException.class)
    public JsonResult handleServletException(ServletException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
    }

    //RuntimeException，运行时错误，服务器内部错误
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public JsonResult runTimeException(Throwable ex) {
        LOGGER.warn(ex.getMessage());
        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "运行时异常!");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public JsonResult handleThrowable(Throwable e) {
        LOGGER.error(e.getMessage());
        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
    }

    //Exception，全局异常
    @ExceptionHandler(Exception.class)
    public JsonResult globalException(Throwable ex) {
        LOGGER.warn(ex.getMessage());
        if (ex instanceof NoHandlerFoundException) {
            return new JsonResult(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
        } else {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "出现异常!");
        }
    }
}
