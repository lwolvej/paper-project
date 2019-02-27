package org.duohuo.paper.exceptions;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.duohuo.paper.model.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lwolvej
 */
@RestControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserException.class)
    public JsonResult handle401(UserException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(ExcelException.class)
    public JsonResult handleExcelException(ExcelException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.NOT_ACCEPTABLE.value(), "检查excel格式是否错误,或联系开发人员!");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public JsonResult handleNotFoundException(NotFoundException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.NOT_FOUND.value(), "资源未找到");
    }

    //CustomException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public JsonResult handleCustomException(CustomException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    //ShiroException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public JsonResult handle401(ShiroException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), "身份验证未通过!");
    }

    //AuthorizationException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public JsonResult handle401(AuthorizationException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), "授权不通过!");
    }

    // UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public JsonResult handle401(UnauthorizedException ex) {
        LOGGER.warn(ex.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), "登录不合法");
    }

    //CaptchaException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CaptchaException.class)
    public JsonResult handleCaptchaException(CaptchaException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.UNAUTHORIZED.value(), "验证码异常!");
    }

    //XssException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(XssException.class)
    public JsonResult handleXssException(XssException e) {
        LOGGER.warn(e.getMessage());
        return new JsonResult(HttpStatus.BAD_REQUEST.value(), "检测到跨站脚本攻击!");
    }

    //RuntimeException
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public JsonResult runTimeException(Throwable ex) {
        LOGGER.warn(ex.getMessage());
        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "运行时异常!");
    }

    //Exception
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public JsonResult globalException(Throwable ex) {
        LOGGER.warn(ex.getMessage());
        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "出现异常!");
    }
}
