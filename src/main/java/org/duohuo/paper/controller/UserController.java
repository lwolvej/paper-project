package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.duohuo.paper.facade.UserFacade;
import org.duohuo.paper.model.dto.UserDto;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lwolvej
 */
@Api(description = "用户接口", value = "用户登录注册")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userFacade")
    private UserFacade userFacade;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(@ApiParam(name = "用户实体") @RequestBody UserDto userDto) {
        return userFacade.loginFacade(userDto);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @ResponseBody
    @RequiresRoles("admin")
    public JsonResult register(@ApiParam(name = "用户实体") @RequestBody UserDto userDto) {
        return userFacade.registerFacade(userDto);
    }

    @ApiOperation(value = "后台允许")
    @GetMapping("/manage")
    @RequiresRoles("admin")
    public JsonResult redirectUpload() {
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(), true);
    }
}
