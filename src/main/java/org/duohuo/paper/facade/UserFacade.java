package org.duohuo.paper.facade;

import org.duohuo.paper.exceptions.UserException;
import org.duohuo.paper.model.dto.UserDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.UserService;
import org.duohuo.paper.utils.RegexUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("userFacade")
public class UserFacade {

    @Resource(name = "userServiceImpl")
    private UserService userService;


    public JsonResult loginFacade(UserDto userDto) {
        validation(userDto);
        return userService.login(userDto.getUserName(), userDto.getPassword(), userDto.getUuid(), userDto.getCode());
    }

    public JsonResult registerFacade(UserDto userDto) {
        validation(userDto);
        return userService.register(userDto.getUserName(), userDto.getPassword(), userDto.getUuid(), userDto.getCode());
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
        if (!RegexUtil.userNameValidation(userDto.getUserName())) {
            throw new UserException("用户名不符合规范");
        }
        if (!RegexUtil.passwordValidation(userDto.getPassword())) {
            throw new UserException("密码不符合规范");
        }
    }
}
