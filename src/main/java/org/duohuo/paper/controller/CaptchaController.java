package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.duohuo.paper.annotation.RequestLimit;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.CaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lwolvej
 */
@Api(description = "验证码接口", value = "获取验证码")
@RestController
public class CaptchaController {

    @Resource(name = "captchaServiceImpl")
    private CaptchaService captchaService;

    @RequestLimit(count = 20)
    @ApiOperation(value = "验证码获取")
    @GetMapping("/captcha")
    @ResponseBody
    public JsonResult captcha() {
        return captchaService.getCaptcha();
    }
}
