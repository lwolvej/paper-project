package org.duohuo.paper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.duohuo.paper.exceptions.UserException;
import org.duohuo.paper.model.dto.UserDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.UserService;
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

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(@ApiParam(name = "用户实体") @RequestBody UserDto userDto) {
        validation(userDto);
        return userService.login(userDto.getUserName(), userDto.getPassword(), userDto.getUuid(), userDto.getCode());
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @ResponseBody
    @RequiresRoles("admin")
    public JsonResult register(@ApiParam(name = "用户实体") @RequestBody UserDto userDto) {
        validation(userDto);
        return userService.register(userDto.getUserName(), userDto.getPassword(), userDto.getUuid(), userDto.getCode());
    }

    @ApiOperation(value = "后台允许")
    @GetMapping("/manage")
    @RequiresRoles("admin")
    public JsonResult redirectUpload() {
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(), true);
    }

    private void validation(UserDto userDto) {
        if (userDto == null) {
            throw new UserException("注册信息未提交!");
        }
        if (userDto.getUserName() == null || userDto.getPassword() == null) {
            throw new UserException("缺少用户名或密码!");
        }
        if (userDto.getUuid() == null || userDto.getCode() == null) {
            throw new UserException("验证信息不全!");
        }
    }
}
